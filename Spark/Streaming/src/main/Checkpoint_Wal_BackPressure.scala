/**
  * Created by Saddam Khan on 5/14/2018.
  */

import org.apache.spark.streaming._
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}

object Checkpoint_Wal_BackPressure {
  
   // Using SparkContext i.e. 1.x
  println("Let's try doing Checkpointing_WAL & handling Backpressure using SparkContext i.e. 1.x!")
  
  val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Checkpointing_WAL_Backpressure")
  
  // Enabling WAL i.e. Write Ahead Logs
  sparkConf.set("spark.streaming.receiver.writeAheadLog.enable", "true")     // This feature is not supported from Structured Streaming i.e. 2.x or can be tried using "option("spark.streaming.receiver.writeAheadLog.enable", "true")" in readStream under sparkSession from Spark 2.x
  
  // Enabling Backpressure i.e. processing data at faster rate when data arrives 
  sparkConf.set("spark.streaming.backpressure.enabled","true")               // This feature is not supported from Structured Streaming i.e. 2.x or can be tried using "option("spark.streaming.backpressure.enabled","true")" in readStream under sparkSession from Spark 2.x
  
  // Create Spark Context & Spark SQL Context
  val sc = new SparkContext(sparkConf)
  val sqlContext = new org.apache.spark.sql.SQLContext(sc)
  import sqlContext.implicits._
  
  // Suppress console logs     
  sc.setLogLevel("ERROR")
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)
  
  // Initialise streaming context
  val ssc = new StreamingContext(sparkConf, Seconds(2))
    
  // Checkpointing to persist the DAG
  ssc.checkpoint("/checkpoint_dir_path")                                     // From spark 2.x there is no separate streaming context or flow is established per query, therefore Checkpointing will happen at query level not at context or session level, for examples refer KafkaSink.scala code 
  
  // Stop streaming context
  ssc.awaitTermination()

  // Stop spark context
  sc.stop()
  
}
