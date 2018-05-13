/**
  * Created by Saddam Khan on 5/14/2018.
  */

import java.text.SimpleDateFormat
import java.sql.Timestamp
import org.apache.spark.sql.streaming._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}
import org.apache.calcite.avatica.ColumnMetaData.StructType
import org.apache.spark.sql.types.StructType

object SocketStream {
  
   // Using SparkSession i.e. 2.x
  println("Let's try Socket Streaming using Spark Structured Streaming i.e. 2.x!")
  
  // Create Spark Session
  val sparkSession = SparkSession.builder.master("local[*]").appName("SocketStream").getOrCreate()
  import sparkSession.implicits._
  
  // Suppress console logs
  sparkSession.sparkContext.setLogLevel("ERROR")
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)

          
  // Create receiver handle i.e. Read text from socket
  val socketDF = sparkSession
          .readStream
          .format("socket")
          .option("host", "localhost")
          .option("port", 9999)
          .load()
  
  println("Streaming Status of Kafka Source : " + socketDF.isStreaming)    // Returns True for DataFrames that have streaming sources
  
  // Display dataFrame schema
  socketDF.printSchema()
  
  // Store output as csv file
  val write_query = socketDF 
          .writeStream 
          .trigger(ProcessingTime("10 seconds")) 
          .format("csv")                                                  // avro-json-orc-parquet-text-kafka works same way for all
          .option("checkpointLocation","src/main/filesink/chkpoint") 
          .option("path","src/main/filesink/output") 
          .start()
          
  write_query.awaitTermination()                                          // Batch Stream | write_query.awaitTermination(batch_stream_interval)
  
  // Stop SparkSession context
  sparkSession.stop()
  
}
