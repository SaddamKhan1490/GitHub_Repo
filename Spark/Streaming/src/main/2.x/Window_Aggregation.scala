/**
  * Created by Saddam Khan on 5/14/2018.
  */

import java.text.SimpleDateFormat
import java.sql.Timestamp
import org.apache.spark.sql.functions._
import org.apache.spark.sql.streaming._
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.expressions.Window
import org.apache.log4j.{Level, Logger}

object Window_Aggregation {
  
  // Using SparkSession i.e. 2.x
  println("Let's try Window Aggregation using Spark Structured Streaming i.e. 2.x!")
  
  // Create Spark Session
  val sparkSession = SparkSession.builder.master("local[*]").appName("Window_Aggregation").getOrCreate()
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
          
  println("Streaming Status of Kafka Source : "+ df.isStreaming)
  
  // Create date_time object
  val dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  
  // Convert data type of date_time object i.e. from DateTime to String
  val date_to_string = dateFormat.toString()
  
  print("Query Starting Time :"+date_to_string)
      
  // Query intermediate received data i.e. apply transformation
  val data = df.selectExpr("CAST(value AS STRING)", "from_unixtime(CAST(timestamp AS LONG),'YYYY-MM-dd HH:mm:ss')").as[(String, Timestamp)]
  
  // Process intermediate received data i.e. apply action
  val results = data
                      .flatMap(
                           value => value._1.split(" ")
                          .map(word => {(word, value._2)})
                     ).toDF("word","timestamp")
               
  // GroupBy & Count over single column in given window
  val windowed_count = results
                       .groupBy(
                        window ($"timestamp", "30 seconds ", "10 seconds")  // Window can be applied only on Timestamp column 
                      ).count().alias("WatermarkCount")
                       .orderBy("window")
  
  // Arithmetic aggregation and Sorting over window                     
  val windowed_avg = results
                       .agg(max(                                            // Avg-Sum-Min-Max can also be applied
                        window ($"timestamp", "30 seconds ", "10 seconds")  // Window can be applied only on Timestamp column 
                        ))
                       .orderBy($"window".desc)
  
  // GroupBy and Sort over multiple column 
  val windowed_with_multi_column_count = results
                       .groupBy(
                        $"word", 
                        window ($"timestamp", "30 seconds ", "10 seconds")  // Window can be applied to multiple  on Timestamp column, but aggregation & GroupBy can be applied to multiple column ex: here, on Timestamp column along with Word column
                      ).count().alias("WatermarkCount")
                       .sort("window")
   
  // Use window with PartitionBy i.e. create partition window
  val partitionWindow = Window
                       .partitionBy($"deptno")
                       .orderBy($"sal".desc) 
  
  // Compute Row_Number per department based on the salary
  val rowNumberTest = row_number().over(partitionWindow)
  val row_number_df = results
                        .select($"*", rowNumberTest as "row_number")
                        .show()

  // Compute Running_Sum per department based on the salary
  val sumTest = sum($"sal").over(partitionWindow)
  val cummulative_sum = results
                        .select($"*", sumTest as "running_total")
                        .show()

  // Compute Lead per department based on the salary
  val leadTest = lead($"timestamp", 1, 0).over(partitionWindow)
  val lead_df = results
                        .select($"*", leadTest as "next_val")
                        .show()

  // Compute Lag per department based on the salary
  val lagTest = lag($"timestamp", 1, 0).over(partitionWindow)
  val lag_df = results
                         .select($"*", lagTest as "prev_val")
                         .show()
                         
  // Collect data for specified time interval before processing i.e. here 1 second
  Thread.sleep(1000)
  
  // Display output on console
  val query = results
                         .writeStream
                         .format("console")                      // Here can also use partitionBy(column_name), in case if we are writing back processed output to file or table
                         .option("truncate","false")             // Here truncate is used to avoid overwrite
                         .trigger(ProcessingTime("10 seconds"))  // Here result will be dispalyed after an interval of 10 seconds for each processed batch
                         .outputMode("append")                   // Here we can change the mode to Update or Complete( i.e. No need of checkpointing) as well depending on our use case
                         .start()
  
  query.awaitTermination()
  
  // Stop SparkSession context
  sparkSession.stop()
  
}
