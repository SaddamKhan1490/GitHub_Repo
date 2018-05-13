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

object TerradataConnector extends App{
  
    // Using SparkSession i.e. 2.x
    println("Let's try Read And Write to Terradata using Spark Structured Streaming i.e. 2.x!")
    
    println("Constructing Terradata Source...")
    
    // Create Spark Session
    val sparkConf = new SparkConf().setAppName("Log Suppression 1.x").setMaster("local[*]").set("spark.streaming.receiver.writeAheadLog.enable", "true")
    val sc = new SparkContext(sparkConf)
    val sqlContext = new org.apache.spark.sql.SQLContext(sc)
    import sqlContext.implicits._
  
    // Suppress console logs     
    sc.setLogLevel("ERROR")
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)
  
    // Create JDBC connection object
    val jdbcDF = sqlContext.load("jdbc", Map(
    "url" -> "jdbc:teradata://<server_name>, TMODE=TERA, user=my_user, password=*****",
    "dbtable" -> "schema.table_name", // here also can be select query
    "driver" -> "com.teradata.jdbc.TeraDriver"))
  
    // Construct schema
    val userSchema = new StructType().add("name", "string").add("age", "integer")  
     
    // Create receiver handle
    val streamDF = sqlContext 
                .readStream 
                .option("maxFilesPerTrigger",10) 
                .option("latestFirst","True") 
                .schema(userSchema) 
                .csv("/path/to/directory")
    
    // Join dataFrame
    val joined_metadata = streamDF.join(jdbcDF,$"age" === $"age","left")
       
    println("Constructing JDBC Sink...")
    
    val url = "jdbc:teradata:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(PROTOCOL=TCP)(HOST=x.x.x.x)(PORT=1521)))(CONNECT_DATA=(SERVICE_NAME=fastdb)))"  // MySQL | jdbc:mysql://<mysql_server_ip ex: localhost>:3306/database_name
    val user = "user";
    val pwd = "password";
  
    // Create sink object
    val writer  = new JDBCSink(url, user, pwd)
    
    // Display output on console
    val query = joined_metadata
                .writeStream
              //.foreach(writer)
                .outputMode("append")
                .trigger(ProcessingTime("10 seconds"))
                .start()
                
/*    // Alternative Way for Writing data back to RDBMS*/
                
/*    // Create a Properties() object to hold the parameters.
      import java.util.Properties
      val connectionProperties = new Properties()
      
      var jdbcUsername = "default username"
      var jdbcPassword = "default password"
      connectionProperties.put("user", s"${jdbcUsername}")
      connectionProperties.put("password", s"${jdbcPassword}")
    
      jdbcDF.createOrReplaceTempView("diamond")
      
      import org.apache.spark.sql.SaveMode

      sqlContext.sql("select * from diamonds limit 10").withColumnRenamed("table", "table_number")
     .write
     .mode(SaveMode.Append) // <--- Append or Overwrite to the existing table
     .jdbc(url, "diamonds", connectionProperties)*/
    
    
    query.awaitTermination()
    
    // Stop SparkSession context
    sc.stop()
  
  
}
