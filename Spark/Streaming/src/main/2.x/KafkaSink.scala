/**
  * Created by Saddam Khan on 5/14/2018.
  */

import java.sql.Timestamp
import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}

object KafkaSink extends App{
  
  // Using SparkSession i.e. 2.x
  println("Let's writing the data back to Kafka using SparkSession i.e. 2.x!")
  
  // Create Spark Session
  val sparkSession = SparkSession.builder.master("local").appName("KafkaSink").getOrCreate()
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
                      .option("startingOffsets", "earliest")
                      .load()
  
  // Query intermediate received data i.e. apply transformation
  val data = df.selectExpr("CAST(value AS STRING)", "from_unixtime(CAST(timestamp AS LONG),'YYYY-MM-dd HH:mm:ss')").as[(String, Timestamp)]
  
  // Process intermediate received data i.e. apply action
  val wordsDs = data
                      .flatMap(line => line._1.split(" ")
                      .map(word => {
                                      (word, line._2)
                                   }))
                      .toDF("word", "timestamp")
                      
  // Process intermediate received data i.e. apply action
  val windowedCount = wordsDs
                      .groupBy(
                      window($"timestamp", "30 seconds")
                      ).count()
  
  // Process intermediate received data i.e. apply action
  val result = windowedCount
                     .selectExpr("concat(CAST(window.start AS String)," +
                     "','" +
                     ",CAST(window.end AS String)," +
                     "','" +
                     ",CAST(count AS STRING)) as value")

  // Kafka acting as Sink i.e. writing data back to kafka after receiving from kafka and processing via spark
  val query_kafka = result
                      .writeStream
                      .format("kafka")
                      .outputMode("update")   // Here we can change the mode to Append or Complete as well depending on our use case
                      .option("kafka.bootstrap.servers", "localhost:9092")
                      .option("topic","wcOutput")
                      .option("checkpointLocation", "src/main/kafkaUpdateSink/chkpoint")
                      .start()
              
   query_kafka.awaitTermination()
   
   // Directory file acting as Sink i.e. writing data back to kafka after receiving from kafka and processing via spark
   val query_file = data
                     .writeStream
                     .format("text")
                     .outputMode("append")  // Here we can change the mode to Update or Complete as well depending on our use case
                     .option("checkpointLocation", "src/main/filesink/chkpoint") // Incase of writing output back to file don't forget to checkpoint the directory, but avoid using checkpoint in case if mode is Complete
                     .option("path", "src/main/filesink/output")
                     .start()

   query_file.awaitTermination()
  
   // Stop SparkSession context
   sparkSession.stop()
  
}
