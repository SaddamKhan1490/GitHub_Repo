/**
  * Created by Saddam Khan on 5/14/2018.
  */

  // Using SparkSession i.e. 2.x
  println("Let's try reading messages from Amazon Kinesis using Spark Structured Streaming i.e. 2.x!")
  
  val sparkSession = SparkSession.builder.master("local[*]").appName("KinesisConnector_StructuredStreaming").getOrCreate()
  import sparkSession.implicits._
  
  // Suppress console logs
  sparkSession.sparkContext.setLogLevel("ERROR")
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)
  
  // Reading data from kafka
  val df = sparkSession
          .readStream
          .format("kinesis")
          .option("streamName", "stream_1")
          .option("region", "us-west-1")
          .option("endpointUrl", "pass_endpoint_url_path")
          .option("awsSecretKey", "/key_path/key_name")
          .option("awsAccessKey", "/key_path/key_name")
          .load()   // Start reading data

  // Extracting fields from the received dataFrame i.e. unbounded table
  val data = df.selectExpr("CAST (column_1 as String)", "CAST (column_2 as String)").as[(String,String)]
  
  // SQL way of extracting data
  /*  df.createOrReplaceTempView("StreamData")
  		sparkSession.sql("SELECT Key, Value from StreamData")*/
 
  // Printing output i.e. extracted kafka messages, on the console 
  val query = data
          .writeStream
          .format("console")
          .outputMode("append")
          .start() // Start writing data
  
  query.awaitTermination()
  
  // Stop SparkSession context
  sparkSession.stop()
  
}
