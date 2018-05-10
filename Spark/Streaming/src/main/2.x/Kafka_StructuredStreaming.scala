/**
  * Created by Saddam Khan on 5/14/2018.
  */
  
import org.apache.spark.sql.streaming._
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}

object Kafka_StructuredStreaming extends App {
 
  // Using SparkSession i.e. 2.x
  println("Let's try reading messages from Kafka using Spark Structured Streaming i.e. 2.x!")
  
  // Create Spark Session
  val sparkSession = SparkSession.builder.master("local").appName("KafkaWordCount_StructuredStreaming").getOrCreate()
  import sparkSession.implicits._
  
  // Suppress console logs
  sparkSession.sparkContext.setLogLevel("ERROR")
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)
  
  // Create spark streaming receiver for reading data from kafka
  val df = sparkSession
            .readStream
            .format("kafka")
            .option("kafka.bootstrap.servers", "localhost:9092")
            .option("subscribe", "Kafka_WordCount")
            .option("startingOffsets", "earliest")
            .option("rowsPerSecond", "1000")
            .load()                                                 // Start reading data 

  /*        // Alternative way to read message from Kafka
          	sparkSession
            .readStream
            .format("kafka")
            .option("kafka.bootstrap.servers", "host1:port1,host2:port2")
            .option("subscribe", "topic1")
            .load()
            .selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
            .groupBy("key")
            .agg(count("*").as("Count_Per_Key"))
            .writeStream
            .format("kafka")
            .option("kafka.bootstrap.servers", "host1:port1,host2:port2")
            .option("topic", "topic1")
            .trigger(Trigger.Continuous("1 second"))  // only change in query
            .start()*/
  
  // Extracting fields from the received dataFrame i.e. unbounded table
  val data = df.selectExpr("CAST (Key as String)", "CAST (Value as String)").as[(String,String)]
  
  
  /*  df.createOrReplaceTempView("TopicData")
    	sparkSession.sql("SELECT Key, Value from TopicData")*/
 
  // Printing output i.e. extracted kafka messages, on the console 
  val query = data
            .writeStream
            .format("console")
            .outputMode("append")
            .start()                                               // Start writing data
  
  query.awaitTermination()
  
  // Stop SparkSession context
  sparkSession.stop()
  
}
