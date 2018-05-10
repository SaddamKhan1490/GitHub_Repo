/**
  * Created by Saddam Khan on 5/14/2018.
  */

import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}

object KafkaWordCount_StructuredStreaming extends App {
  
  // Using SparkSession i.e. 2.x
  println("Let's try Kafka Word Count using Spark Structured Streaming i.e. 2.x!")
  
  // Create SparkSession context
  val sparkSession = SparkSession.builder.master("local").appName("KafkaWordCount_StructuredStreaming").getOrCreate()
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
                .option("rowsPerSecond", "1000")
                .load()                                                 // Start reading data
  
  // Query intermediate received data i.e. apply transformation
  val data = df.selectExpr("CAST (Key as String)", "CAST (Value as String)").as[(String,String)]
  
  // Process intermediate received data i.e. apply action
  val results = data
                .map(_._2)
                .flatMap(value => value.split("\\s+"))
                .groupByKey(_.toLowerCase)
                .count()
                
  // Display output on console
  val query = results
                .writeStream
                .format("comsole")
                .outputMode("complete")
                .option("checkpointLocation", "src/main/chkpoint_dir")
                .start()                                                 // Start writing data
  
  query.awaitTermination()
  
  // Stop SparkSession context
  sparkSession.stop()
  
}
