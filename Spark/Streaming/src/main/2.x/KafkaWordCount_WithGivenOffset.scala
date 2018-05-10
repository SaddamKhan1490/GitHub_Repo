
/**
  * Created by Saddam Khan on 5/14/2018.
  */

import java.sql.Timestamp
import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}

object KafkaWordCount_WithGivenOffset {
  
  def main(args: Array[String]): Unit = {
    
      // Using SparkSession i.e. 2.x
      println("Let's try reading message from single topics with specfied offset i.e. starting offset using Spark Structured Streaming i.e. 2.x!")
    
      // Create Spark Session
      val sparkSession = SparkSession.builder
                         .master("local[*]")
                         .appName("KafkaWordCount_WithGivenOffset")
                         .getOrCreate()

      import sparkSession.implicits._
      
      // Suppress console logs
      sparkSession.sparkContext.setLogLevel("ERROR")
      Logger.getLogger("org").setLevel(Level.OFF)
      Logger.getLogger("akka").setLevel(Level.OFF)
      
      // Create receiver
      val df = sparkSession
                         .readStream
                         .format("kafka")
                         .option("kafka.bootstrap.servers", "localhost:9092")
                         .option("subscribe", "wordcount")
                         .option("startingOffsets", """{"wordcount":{"0":15}}""") // Specify trigger offset; similarly we can also specify only stopping offset and set the receive mode accordingly
                         .load()
      
      // Query intermediate received data i.e. apply transformation
      val data = df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)").as[(String, String)]

      // Process intermediate received data i.e. apply action
      val results = data
                          .map(_._2)
                          .flatMap(value => value.split("\\s+"))
                          .groupByKey(_.toLowerCase)
                          .count()
                          
      // Display output on console    
      val query = results
                          .writeStream
                          .format("console")
                          .outputMode("complete")                                  // Checkpoint is NOT required in case we are writing in Complete Mode
                          .start()
      
      query.awaitTermination()
      
      // Stop SparkSession context
      sparkSession.stop()

    }
}
