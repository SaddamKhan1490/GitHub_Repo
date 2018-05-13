/**
  * Created by Saddam Khan on 5/14/2018.
  */
  

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.explode
import org.apache.spark.{SparkConf, SparkContext}

object JsonParsing extends App {

      /*
      println("Welcome to JSON Parsing using SparkContext i.e. 1.x!")
      
      
      // Create Spark Context
      val sparkConf = new SparkConf().setAppName("Simple Parsing Nested JSON Application").setMaster("local[*]")
      val sc = new SparkContext(sparkConf)
      val sqlContext = new org.apache.spark.sql.SQLContext(sc)
      import sqlContext.implicits._
     
      // Suppress console logs     
      sc.setLogLevel("ERROR")
      Logger.getLogger("org").setLevel(Level.OFF)
      Logger.getLogger("akka").setLevel(Level.OFF)
    
    	// Create dataFrame
      val df = sqlContext.read.json("C:\\Users\\Lenovo\\Desktop").toDF
      
      // Display dataFrame Schema
      df.printSchema()
    
    	// Parse JSON dataFrame
      val df0 = df.withColumn("lang", explode($"lang"))
        .withColumn("id", $"lang"(0))
        .withColumn("langs", $"lang"(1))
        .withColumn("type", $"lang"(2))
        .drop("lang")
        .withColumnRenamed("langs", "lang")
        .show(false)
    
     // Display content of parsed field
     df.select("lang.id").schema.head.dataType
     
     // Stop Spark Context
     sc.stop()
     */

      /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
      
      println("Welcome to JSON Parsing using SparkSession i.e. 2.x!")
      
      // Create Spark Sessions
      val sparkSession = SparkSession.builder.master("local[*]").appName("Simple example of JSON dataFrame...").getOrCreate()
      import sparkSession.implicits._
      
      // Create dataFrame
      val df1 = sparkSession.read.json("C:\\Users\\Lenovo\\Desktop")//.as[String]
      
      // Display content of dataFrame
      df1.printSchema()
      
      // Suppress console logs
      sparkSession.sparkContext.setLogLevel("ERROR")
      Logger.getLogger("org").setLevel(Level.OFF)
      Logger.getLogger("akka").setLevel(Level.OFF)
  
      // Parse JSON dataFrame 
      val df2 = df1.withColumn("lang", explode($"lang"))
        .withColumn("id", $"lang"(0))
        .withColumn("langs", $"lang"(1))
        .withColumn("type", $"lang"(2))
        .drop("lang")
        .withColumnRenamed("langs", "lang")
        //.show(false)
  
      // Copying selected columns of dataFrame which got created after loading data from JSON file into other dataFrame
      val temp = df2.select("group", "id", "lang", "type")
      temp.show()
      
      // Stop Spark Session
      sparkSession.stop()

}
