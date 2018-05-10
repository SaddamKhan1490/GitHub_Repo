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

object Window_Watermark extends App{
 
  // Using SparkSession i.e. 2.x
  println("Let's try Watermarking over Window using Spark Structured Streaming i.e. 2.x!")
  
  // Create Spark Session
  val sparkSession = SparkSession
                      .builder
                      .master("local[*]")
                      .appName("Window_Watermark")
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
                        .option("subscribe", "word_count_topic")
                        .option("startingOffsets", "earliest")
                        .load()
          
  println("Streaming Status of Kafka Source : "+df.isStreaming)
          
  // Create date_time object
  val dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
      
  // Convert data type of date_time object i.e. from DateTime to String
  var string_dateFormat  = dateFormat.toString()
      
  print("Query Starting Time :"+string_dateFormat)
      
  // Query intermediate received data i.e. apply transformation  
  val data = df.selectExpr("CAST(value AS STRING)", "from_unixtime(CAST(timestamp AS LONG),'YYYY-MM-dd HH:mm:ss')").as[(String, Timestamp)]
  
  // Process intermediate received data i.e. apply action
  val results = data
                        .flatMap(
                             value => value._1.split(" ")
                            .map(word => {(word, value._2)})
                       ).toDF("word","timestamp")
  
  // Process intermediate received data i.e. apply action
  val unique_per_watermark_window = results
                         .withWatermark("timestamp", "10 seconds")      // Watermark without window 
                         .dropDuplicates("Unique_Records","timestamp")
                         .count()
                         
  // Process intermediate received data i.e. apply action              
  val windowed_count = results
                         .withWatermark("timestamp", "10 seconds")       // WaterMark window can be applied only on Timestamp column 
                         .groupBy(
                          window ($"timestamp", "30 seconds ", "10 seconds")
                        ).count().alias("WatermarkCount")
                         .orderBy("window")
                         
  // Process intermediate received data i.e. apply action                    
  val windowed_with_multi_column_count = results
                         .withWatermark("timestamp", "10 seconds")       // WaterMark window can be applied only on Timestamp column, but aggregation & GroupBy can be applied to multiple column ex: here, on Timestamp column along with Word column
                         .groupBy(
                          $"word", 
                          window ($"timestamp", "30 seconds ", "10 seconds")
                        ).count().alias("WatermarkCount")
                         .orderBy("window")
                      
  // Impose extra delay on top of Watermark Window i.e. here 1 second
  Thread.sleep(1000)
  
  // Display output on console
  val query = results
                         .writeStream
                         .format("console")                              // Here can also use partitionBy(column_name), in case if we are writing back processed output to file or table
                         .option("truncate","false")                     // Here truncate is used to avoid overwrite
                         .trigger(ProcessingTime("10 seconds"))          // Here result will be dispalyed after an interval of 10 seconds for each processed batch
                         .outputMode("append")                           // Here we can change the mode to Update or Complete( i.e. No need of checkpointing) as well depending on our use case
                         .start()
  
  query.awaitTermination()
  
  // Stop SparkSession context
  sparkSession.stop()
  
}
