
/**
  * Created by Saddam Khan on 5/14/2018.
  */

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.functions.{col, min}
import org.apache.log4j.{Level, Logger}

object Check_SourceIsStreaming {
  
   
  // Using SparkSession i.e. 2.x
  println("Let's try verifying wether the source is streaming or not in Spark Structured Streaming i.e. 2.x!")
  
  // Create Spark Session
  val sparkSession = SparkSession.builder.master("local").appName("Check_SourceIsStreaming").getOrCreate()
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
  
  // Identify is source is streaming
  println("Streaming Status of Kafka Source : "+ df.isStreaming)
  
  // Display dataFrame schema
  df.printSchema()
  
  // Display output on console
  val query = df
                .writeStream
                .format("console")
                .outputMode("append")                // Here, can also use "update or complete" mode as well
                .start()
  
  query.awaitTermination()
  
  // Stop SparkSession context
  sparkSession.stop()
  
}
