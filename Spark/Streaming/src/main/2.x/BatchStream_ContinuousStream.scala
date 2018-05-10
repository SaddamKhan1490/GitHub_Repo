
/**
  * Created by Saddam Khan on 5/14/2018.
  */
  
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}

object BatchStream_ContinuousStream {
  
  // Using SparkSession i.e. 2.x
  println("Let's try to establish continuous streaming and streaming batch pipeline using Spark Structured Streaming i.e. 2.x!")
  
  // Create Spark Session
  val sparkSession = SparkSession.builder.master("local[*]").appName("BatchStream_ContinuousStream").getOrCreate()
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
  
  // Query intermediate received data i.e. apply transformation
  val data = df.selectExpr("CAST (Key as String)", "CAST (Value as String)").as[(String,String)]
  
  // Process intermediate received data i.e. apply action
  val results = data
                .map(_._2)
                .flatMap(value => value.split("\\s+"))
                .groupByKey(_.toLowerCase)
                .count()
  
  val query_continous_stream = results
                .writeStream
                .format("console")
                .outputMode("append")                // Here, can also use "update or complete" mode as well
                .start()
                
  // Continuous Stream i.e. Continuous READ & WRITE
  query_continous_stream.awaitTermination()
  
  // Streaming Batch Stream i.e. Semi-Continuous READ & WRITE
  Thread.sleep(5000)                                 // Can use window frame to process streaming batch
  
  val query_batch_stream = results
                .writeStream
                .format("console")
                .outputMode("append")               // Here, can also use "update or complete" mode as well
                .start()
  
  // Batch Stream i.e. READ & WRITE per job run basis ex -> Camus
  query_batch_stream.awaitTermination(5000)         // Pull all data then Timeout after waiting for 5 seconds
  
  // Stop SparkSession context
  sparkSession.stop()
  
}
