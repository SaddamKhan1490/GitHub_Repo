/**
  * Created by Saddam Khan on 5/14/2018.
  */
  
import java.text.SimpleDateFormat
import java.sql.Timestamp
import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}

object KafkaWordCount_With_IngestionTimestamp extends App{
  
      // Using SparkSession i.e. 2.x
      println("Let's try Word Count along with Message Ingestion Timestamp using Spark Structured Streaming i.e. 2.x!")
      
      // Create Spark Session
      val sparkSession = SparkSession.builder.master("local[*]").appName("KafkaWordCount_With_IngestionTimestamp").getOrCreate()
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
                            .load()
      
      // Create date_time object
      val dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
      
      // Convert data type of date_time object i.e. from DateTime to String
      var string_dateFormat  = dateFormat.toString()
      
      print("Query Starting Time :"+string_dateFormat)
      
      // Query intermediate received data i.e. apply transformation
      val data = df.selectExpr("CAST(value AS STRING)", "from_unixtime(CAST(timestamp AS LONG),'YYYY-MM-dd HH:mm:ss')").as[(String, Timestamp)]
      
      // Process intermediate received data i.e. apply action
      val results = data
                           .flatMap(
                                value => value._1.split(" ")
                                .map(word => {(word, value._2)})
                          ).toDF("word","timestamp")
      
      // Collect data for specified time interval before processing i.e. here 1 second
      Thread.sleep(1000)
      
      // Process intermediate received data i.e. apply action
      val windowed_count = results
                           .groupBy(
                            window ($"timestamp", "30 seconds ")
                          ).count()
                           .orderBy("window")
      
      // Display output on console
      val query = results
                           .writeStream
                           .format("console")
                           .option("truncate","false")
                           .outputMode("complete")                                      // Here we can change the mode to Update or Complete as well depending on our use case
                           .start()
      
      query.awaitTermination()
      
      // Stop SparkSession context
      sparkSession.stop()
  
}
