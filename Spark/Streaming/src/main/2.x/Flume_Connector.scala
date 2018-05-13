/**
  * Created by Saddam Khan on 5/14/2018.
  */
  

import java.text.SimpleDateFormat
import java.sql.Timestamp
import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.types.StructType
import org.apache.spark.streaming.flume._
import org.apache.spark.streaming._

 
object FlumeConnector extends App {
  
  // Using SparkSession i.e. 2.x
  println("Let's try reading messages from Flume using Spark Structured Streaming i.e. 2.x!")
  
  // Create Spark Session
  val sparkSession = SparkSession.builder.master("local").appName("FlumeConnector").getOrCreate()
  import sparkSession.implicits._
  
  // Suppress console logs
  sparkSession.sparkContext.setLogLevel("ERROR")
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)
  
  // Reading data from kafka           
  val sparkConf = new SparkConf().setAppName("FlumeEventCount")
  val batchInterval = Milliseconds(2000)
  val ssc = new StreamingContext(sparkConf, batchInterval)                         // Start reading data
  
  // Create receiver agent
  val flumeStream_df = FlumeUtils.createPollingStream(ssc,"localhost", 9000)       // Reference | https://spark.apache.org/docs/2.2.0/streaming-flume-integration.html
  
  // Extracting fields from the received dataFrame i.e. unbounded table
  val data = flumeStream_df.count()
 
  // Printing output i.e. extracted kafka messages, on the console 
  val query = data.saveAsTextFiles("Flume", "Output")                             // Start Saving data to file                    
  
  // Consuming data in batch
  ssc.awaitTerminationOrTimeout(3000)
  
  // Stop Spark Session
  sparkSession.stop()
  
}
