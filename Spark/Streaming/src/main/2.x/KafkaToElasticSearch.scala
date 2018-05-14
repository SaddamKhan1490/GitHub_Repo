/**
  * Created by Saddam Khan on 5/14/2018.
  */
  
import org.apache.spark.sql.streaming._
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}
import org.elasticsearch.spark._
import org.elasticsearch.spark.rdd.EsSpark 

object KafkaToElasticSearch extends App {
 
  // Using SparkSession i.e. 2.x
  println("Let's try reading messages from Kafka and ingesting to Elastic Search using Spark Structured Streaming i.e. 2.x!")
  
  // Create Spark Session
  val sparkSession = SparkSession.builder.master("local").appName("KafkaToElasticSearch").getOrCreate()
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
            .load()                                                          // Start reading data 
  
  // Extract fields from the received dataFrame i.e. unbounded table
  val data = df.selectExpr("CAST (Key as String)", "CAST (Value as String)").as[(String,String)]
  
  
  /*  df.createOrReplaceTempView("TopicData")
    	sparkSession.sql("SELECT Key, Value from TopicData")*/
 
  
  // Display dataFrame schema
  data.printSchema()

  // Write output to file i.e. extracted kafka messages to elasticsearch 
  val query = data
            .writeStream
            .format("org.elasticsearch.spark.sql")
            .option("es.net.http.auth.user","username")
            .option("es.net.http.auth.pass","password")
            .option("es.nodes", "127.0.0.1")                                  // Can also specify "localhost" inplace of "127.0.0.1"
            .option("es.port","9200")
            .option("es.index.auto.create", "true")
            .option("checkpointLocation", "/tmp")
            .outputMode("append")
            .trigger(Trigger.ProcessingTime(1000))
            .start("test/hello")                                              // Start ingesting data to elastic search at index/label and ingested data i.e. input records from kafka, can be verified by hitting URL : "curl -XGET https://ip_address:9200/index(i.e. here test)"  
   
  query.awaitTermination()  
  
  // Stop SparkSession context
  sparkSession.stop()
  
}
