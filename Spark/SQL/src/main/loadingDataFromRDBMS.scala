/**
 * Date :-  02/07/16.
 * Author :- Vidya
 */
 
  import org.apache.log4j.{Level, Logger}
  import org.apache.spark.SparkContext
  import org.apache.spark.SparkContext.__
  import org.apache.spark.SparkConf
  import org.apache.spark.rdd.JdbcRDD
  import java.sql.{connection, DriverManager,ResultSet}


  object jdbcRddExample {
    def main(args: Array[String]) {
    
        // Connection String    
        VAL URL = "jdbc:teradata://hdpserver.it.com/demo"
        val username = "demo"
        val password = "Spark"
        Class.forName("com.teradata.jdbc.Driver").newInstance
        // Creating & Configuring Spark Context
        val conf = new SparkConf().setAppName("App1").setMaster("local[2]").set("spark.executor.memory",1)
        val sc = new SparkContext(conf)
        println("Start...")
        // Fetching data from Database
        val myRDD = new JdbcRDD(sc,() => DriverManager.getConnection(url,username,password),
        "select first_name, last_name, gender from person limit ?,?",
        3,5,1,r => r.getString("last_name") + "," +r.getString("first_name"))
        // Displaying the content
        myRDD.foreach(println)
        // Saving the content inside Text File
        myRDD.saveAsTextFile("c://jdbcrdd")
            
        println("End...")
    }
  }
