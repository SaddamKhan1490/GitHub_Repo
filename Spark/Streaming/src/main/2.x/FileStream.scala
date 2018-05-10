
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
import org.apache.spark.sql.types.StructType

case class userSchema(name: String, age:Int)

object FileStream extends App {
  
  // Using SparkSession i.e. 2.x
  println("Let's try to stream data from file using Spark Structured Streaming i.e. 2.x!")
  
  // Create Spark Session
  val sparkSession = SparkSession.builder.master("local").appName("FileStream").getOrCreate()
  import sparkSession.implicits._
  
  // Suppress console logs
  sparkSession.sparkContext.setLogLevel("ERROR")
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)

          
  // Read all the csv files written atomically in a directory
  val userSchema = new StructType().add("name", "string").add("age", "integer")
  
  // Create receiver
  val csvDF = sparkSession
          .readStream
          .format("csv")                                           // avro-json-orc-parquet-text-kafka works same way for all
          .option("sep", ";")
          .option("maxFilesPerTrigger",10) 
          .option("latestFirst","True")
          .schema(userSchema)                                      // specify schema of the csv files
          .csv("/path/to/directory")                               // equivalent to format("csv").load("/path/to/directory") | Similarly can specify avro-json-orc-parquet-text works same way for all
          
  println("Streaming Status of File Source : "+csvDF.isStreaming)
  
  // Display dataFrame schema
  csvDF.printSchema()
  
  // Display output on console
  val write_query = csvDF 
          .writeStream 
          .trigger(ProcessingTime("10 seconds")) 
          .format("csv")                                             // console-avro-json-orc-parquet-text-kafka works same way for all
          .option("checkpointLocation","src/main/filesink/chkpoint") // checkpoint directory for data recovery
          .option("path","src/main/filesink/output")
          .partitionBy("date")                                       // create partition on the fly while storing the data i.e. file or table
          .outputMode("append")                                      // here we can also use Update or Complete mode as well
          .start()
          
  write_query.awaitTermination()                                     // batch Stream | write_query.awaitTermination(batch_stream_interval)
  
  // Stop SparkSession context
  sparkSession.stop()
  
}
