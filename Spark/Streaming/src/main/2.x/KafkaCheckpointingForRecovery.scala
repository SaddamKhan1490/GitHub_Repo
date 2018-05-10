/**
  * Created by Saddam Khan on 5/14/2018.
  */
  
import java.sql.Timestamp
import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}

object KafkaCheckpointingForRecovery {
  
  // Using SparkSession i.e. 2.x
  println("Let's try checkpointing kafka data while writing back to kafka using Spark Structured Streaming i.e. 2.x!")
  
  // Create Spark Session
  val sparkSession = SparkSession.builder.master("local").appName("KafkaCheckpointingForRecovery").getOrCreate()
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
        .option("subscribe", "wordcount")
        .option("startingOffsets", "earliest")
        .load()

  // Query intermediate received data i.e. apply transformation
  val data = df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
        .as[(String, String)]

  // Process intermediate received data i.e. apply action
  val results = data
        .map(_._2)
        .flatMap(value => value.split("\\s+"))
        .groupByKey(_.toLowerCase)
        .mapGroups((a,b)=>(a,b.size))
  //    .mapGroups((a,b)=>(a,10))
        
  // Checkpointing data for recovery purpose in of occourrence of any or some failure
  val query = results.writeStream
        .format("console")
        .option("checkpointLocation", "src/main/console/chkpoint")
        .outputMode("append")
        .start()

  query.awaitTermination(3000)
  
  // Stop SparkSession context
  sparkSession.stop()
}
