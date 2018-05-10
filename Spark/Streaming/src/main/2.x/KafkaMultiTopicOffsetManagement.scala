
/**
  * Created by Saddam Khan on 5/14/2018.
  */

import java.sql.Timestamp
import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}

object KafkaMultiTopicOffsetManagement extends App {
  
    // Using SparkSession i.e. 2.x
    println("Structured Streaming reading message from multiple topics with specfied offset range i.e. starting offset & ending offset using SparkSession i.e. 2.x!")
  
    // Create Spark Session
    val sparkSession = SparkSession
                           .builder
                           .master("local")
                           .appName("KafkaMultiTopicOffsetManagement_StructuredStreaming")
                           .getOrCreate()

    import sparkSession.implicits._
    
    // Suppress console logs
    sparkSession.sparkContext.setLogLevel("ERROR")
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    // Create receiver to subscribe to one topic
    /*    val oneTopifDF = spark
                          .read
                          .format("kafka")
                          .option("kafka.bootstrap.servers", "localhost:9092")
                          .option("subscribe", "wordcount")
                          .option("startingOffsets", "earliest") OR .option("startingOffsets", "latest") OR .option("startingOffsets", """{"wordcount":{"0":15}}""")
                          .load()
          val result_s = oneTopifDF.selectExpr("CAST(value AS STRING)","topic","partition","offset").as[(String,String,String,String)]
         
          result_s.show()*/

    // Create receiver to subscribe to multiple topics
    val multipleTopicdf = sparkSession
                          .read
                          .format("kafka")
                          .option("kafka.bootstrap.servers", "localhost:9092")
                          .option("subscribe", "wordcount,wcOutput")
                          .option("startingOffsets", """{"wordcount":{"0":20},"wcOutput":{"0":0}}""")
                          .option("endingOffsets", """{"wordcount":{"0":25},"wcOutput":{"0":5}}""")
                          .load()                                    // Start reading data
                          
    // Query intermediate received data i.e. apply transformation
    val result_m = multipleTopicdf.selectExpr("CAST(value AS STRING)","topic","partition","offset").as[(String,String,String,String)]
  
    // Display dataFrame i.e. default only 20 records will be displayed
    result_m.show()
    
    // Print output i.e. extracted kafka messages, on the console 
    val query = result_m
                          .writeStream
                          .format("console")
                          .outputMode("append")
                          .start()                                               // Start writing data
    
    query.awaitTermination()
    
    // Stop SparkSession context
    sparkSession.stop()

    
}
