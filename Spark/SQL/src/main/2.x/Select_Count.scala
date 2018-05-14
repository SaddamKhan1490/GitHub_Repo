
/**
  * Created by Saddam Khan on 5/14/2018.
  */

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import org.apache.spark.sql.SQLContext
import org.apache.log4j.{Level, Logger}


object Select_Count {
  
  def main(args: Array[String])= {

    // Create Spark Context
    val conf = new SparkConf().setAppName("Simple Application to demonstrate Select_Count by creating table using RDD_DataFrame").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    import sqlContext.implicits._
    
    // Suppress console logs     
    sc.setLogLevel("ERROR")
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    // List to RDD
    val info = List(("mike", 24), ("joe", 34), ("jack", 55))
    val infoRDD = sc.parallelize(info)

    // RDD to dataFrame
    val people = infoRDD.map(r => Person(r._1, r._2)).toDF()

    // DataFrame to Table
    people.registerTempTable("people")
    people.createTempView("people")

    // Process dataFrame using SQL
    val select_DF = sqlContext.sql("select * from people where age > 30")
    
    // Display content of dataFrame 
    select_DF.show()
    
    // Process dataFrame using SQL
    val count_DF = sqlContext.sql("select count(*) from people where age > 30")

    // Display content of dataFrame 
    count_DF.show()
    
    // Stop Spark Context
    sc.stop()
    
  }
}
