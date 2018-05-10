
/**
  * Created by Saddam Khan on 5/14/2018.
  */
  
import java.text.SimpleDateFormat
import java.sql.Timestamp
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming._
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.types.StructType

class JDBCSink(url: String, user: String, pwd: String) extends org.apache.spark.sql.ForeachWriter[org.apache.spark.sql.Row] {
   
    val driver = "oracle.jdbc.driver.OracleDriver"
    var connection: java.sql.Connection = _
    var statement: java.sql.PreparedStatement = _
    val v_sql = "insert INTO sparkdb.t_cf(EntityId,clientmac,stime,flag,id) values(?,?,to_date(?,'YYYY-MM-DD HH24:MI:SS'),?,stream_seq.nextval)"
  
    // Open connection to JDBC source
    def open(partitionId: Long, version: Long): Boolean = {
      Class.forName(driver)
      connection = java.sql.DriverManager.getConnection(url, user, pwd)
      connection.setAutoCommit(false)
      statement = connection.prepareStatement(v_sql)
      true
    }
  
    // Execute query
    def process(value: org.apache.spark.sql.Row): Unit = {
      statement.setString(1, value(0).toString)
      statement.setString(2, value(1).toString)
      statement.setString(3, value(2).toString)
      statement.setString(4, value(3).toString)
      statement.executeUpdate()        
    }
  
    // Close connection
    def close(errorOrNull: Throwable): Unit = {
      connection.commit()
      connection.close
    }
}

object JDBC_Connector extends App{
  
    // Using SparkSession i.e. 2.x
    println("Let's try Read And Write to JDBC using Spark Structured Streaming i.e. 2.x!")
    
    // Create Spark Session
    val sparkSession = SparkSession.builder.master("local").appName("JDBC_Connector_StructuredStreaming").getOrCreate()
    import sparkSession.implicits._
    
    // Suppress console logs
    sparkSession.sparkContext.setLogLevel("ERROR")
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)
    
    println("Constructing JDBC Source...")
    
    Class.forName("com.mysql.jdbc.Driver")
    val jdbcHostname = "<hostname>"
    val jdbcPort = 3306
    val jdbcDatabaseName ="<database>"
    
    // Create the JDBC URL without passing in the user and password parameters.
    val jdbcUrl = s"jdbc:mysql://${jdbcHostname}:${jdbcPort}/${jdbcDatabaseName}"
    
    // Create a Properties() object to hold the parameters.
    import java.util.Properties
    val connectionProperties = new Properties()
    
    var jdbcUsername = "default username"
    var jdbcPassword = "default password"
    connectionProperties.put("user", s"${jdbcUsername}")
    connectionProperties.put("password", s"${jdbcPassword}")
    
    // Create JDBC connection object
    import java.sql.DriverManager
    val connection = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword)
    connection.isClosed()
  
    // Establish Spark JDBC connection with target table
    val employees_table = sparkSession
                .read
                .jdbc(jdbcUrl, "employees", connectionProperties)
    
    // Query intermediate received data i.e. apply transformation
    val df = employees_table.select("age", "salary").groupBy("age").avg("salary")
    
   /*   //Alternative Way for Reading data from RDBMS
        val df1 = (sparkSession.read.jdbc(url=jdbcUrl,
        table="employees",
        columnName="emp_no",
        lowerBound=1L,
        upperBound=100000L,
        numPartitions=100,
        connectionProperties=connectionProperties).select("name", "age", "salary").where("salary === 10000").explain(true))*/

    // Construct schema
    val userSchema = new StructType().add("name", "string").add("age", "integer")
    
    // Create receiver
    val streamDF = sparkSession 
                .readStream 
                .option("maxFilesPerTrigger",10) 
                .option("latestFirst","True") 
                .schema(userSchema) 
                .csv("/path/to/directory")
    
    // Join dataFrame 
    val joined_metadata = streamDF.join(df,$"age" === $"age","left")
      
    println("Constructing JDBC Sink...")
    
    val url = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=x.x.x.x)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=fastdb)))"  // MySQL | jdbc:mysql://<mysql_server_ip ex: localhost>:3306/database_name
    val user = "user";
    val pwd = "password";
  
    // Create sink object
    val writer  = new JDBCSink(url, user, pwd)
    
    // Display output on console
    val query = joined_metadata
                .writeStream
                .format("console")
              //.foreach(writer)
                .outputMode("append")
                .trigger(ProcessingTime("10 seconds"))
                .start()
                
    /*    // Alternative Way for Writing data back to RDBMS
          import org.apache.spark.sql.SaveMode
    
          sparkSession.sql("select * from diamonds limit 10").withColumnRenamed("table", "table_number")
         .write
         .mode(SaveMode.Append) // <--- Append or Overwrite to the existing table
         .jdbc(jdbcUrl, "diamonds", connectionProperties)*/
     
    query.awaitTermination()
    
    // Stop SparkSession context
    sparkSession.stop()
  
  
}
