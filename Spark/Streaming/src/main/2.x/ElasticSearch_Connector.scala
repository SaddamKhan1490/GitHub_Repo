/**
  * Created by Saddam Khan on 5/14/2018.
  */

import org.apache.spark.sql.streaming._
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}
import org.elasticsearch.spark._
import org.elasticsearch.spark.rdd.EsSpark 

object ElasticSearch_Connector extends App {
 
      // Using SparkSession i.e. 2.x
      println("Let's try reading messages from Elastic Search to various channels using Spark Structured Streaming i.e. 2.x!")
      
      // Create Spark Session
      val sparkSession = SparkSession.builder.master("local").appName("ElasticSearch_Connector").getOrCreate()
      import sparkSession.implicits._
      
      // Suppress console logs
      sparkSession.sparkContext.setLogLevel("ERROR")
      Logger.getLogger("org").setLevel(Level.OFF)
      Logger.getLogger("akka").setLevel(Level.OFF)
      
      // Create spark streaming receiver for reading data from kafka
      val elasticIndex = "index/type"
      val url = "x.x.x.x:9200"
      val reader =sparkSession.read.                                    // Here we can also use "readStream" instead of "read"
         format("org.elasticsearch.spark.sql").
         option("es.net.http.auth.user","username").
         option("es.net.http.auth.pass","password").
         option("es.nodes",url)
    
      println(s"Loading: ${url} ...")
      
      // Start reading data 
      val df = reader.load(elasticIndex) 
      
      // Display dataFrame schema
      df.printSchema()
      
      // Count content of dataFrame
      df.count()      
      
      /*  df.createOrReplaceTempView("IngestedData")
        	sparkSession.sql("SELECT count(*) from IngestedData")*/
      
      // Write output to Elastic Search i.e. messages from elasticsearch 
      val query = df
                      .writeStream
                      .format("org.elasticsearch.spark.sql")
                      .option("es.net.http.auth.user","username")
                      .option("es.net.http.auth.pass","password")
                      .option("es.nodes","127.0.0.1")                                  // Can also specify "localhost" inplace of "127.0.0.1"
                      .option("es.port","9200")
                      .option("es.index.auto.create","true")
                      .option("checkpointLocation","/tmp")
                      .outputMode("append")
                      .trigger(Trigger.ProcessingTime(1000))
                      .start("test/hello")                                              // Start ingesting data to elastic search at index/label and ingested data i.e. input records from kafka, can be verified by hitting URL : "curl -XGET https://ip_address:9200/index(i.e. here test)"  
     
       query.awaitTermination()
      
      // Write output to Kafka i.e. writing data to kafka after receiving from elasticsearch and processing via spark
      val query_kafka = df
                      .writeStream
                      .format("kafka")
                      .outputMode("update")   // Here we can change the mode to Append or Complete as well depending on our use case
                      .option("kafka.bootstrap.servers", "localhost:9092")
                      .option("topic","wcOutput")
                      .option("checkpointLocation", "src/main/kafkaUpdateSink/chkpoint")
                      .trigger(Trigger.ProcessingTime(1000))
                      .start()
              
       query_kafka.awaitTermination()
   
       // Write output to file directory i.e. writing data to file after receiving from elasticsearch and processing via spark
     val query_file = df
                      .writeStream
                      .format("text")
                      .outputMode("append")  // Here we can change the mode to Update or Complete as well depending on our use case
                      .option("checkpointLocation", "src/main/filesink/chkpoint") // Incase of writing output back to file don't forget to checkpoint the directory, but avoid using checkpoint in case if mode is Complete
                      .option("path", "src/main/filesink/output")
                      .trigger(Trigger.ProcessingTime(1000))
                      .start()

        query_file.awaitTermination()
                     
      // Printing output i.e. extracted messages from elastic search, on the console 
      val query_console = df
                      .writeStream
                      .format("console")
                      .outputMode("append")
                      .trigger(Trigger.ProcessingTime(1000))
                      .start()
      
       query_console.awaitTermination()
      
      // Stop SparkSession context
      sparkSession.stop()
  
}
