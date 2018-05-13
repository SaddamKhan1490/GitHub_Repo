/**
  * Created by Saddam Khan on 5/14/2018.
  */
  
import org.apache.spark.SparkConf
import org.apache.spark._
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions._
import scala.util.{ Failure, Success, Try }
import org.apache.spark.sql._
import org.apache.spark.sql.Column
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.execution.datasources.CreateTempViewUsing

class Conversion_ParquetToAvro extends App {
  
  // Using SparkContext i.e. 1.x
  println("Parquet to Avro conversion using SparkContext i.e. 1.x!")
  
  // Create Spark Context
  val sparkConf = new SparkConf().setAppName("Convert_ParquetToAvro").setMaster("local[*]").set("spark.streaming.receiver.writeAheadLog.enable", "true")
  val sc = new SparkContext(sparkConf)
  val sqlContext = new org.apache.spark.sql.SQLContext(sc)
  import sqlContext.implicits._
  
  // Suppress console logs     
  sc.setLogLevel("ERROR")
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)
      
  // Set command line argument variables
  var src_data_path = args(0)
  var dst_data_path = args(1)
  var input_file_format = args(2)                 // We can pass parquet-avro-csv-json-xml-etc..., Here, args(2) = "parquet"
  var output_file_format = args(3)                // We can pass parquet-avro-csv-json-xml-etc..., Here, args(3) = "avro"
  
  
  // Read parquet files
  val parquet_data = sqlContext.read.format(s"com.databricks.spark.${input_file_format}").load(src_data_path)         // Passing path as command line argument, we can also pass direct path as well 
  
  // Create table out of loaded file 
  parquet_data.registerTempTable("Temp_Source")                                                                       // df.CreateTempViewUsing("Temp_Source")
  
  // Query intermediate received data i.e. apply transformation
  val avro_data = sqlContext.sql("select *,CONCAT(ToString(CurrentTime(),'yyyy-MM-dd'),' 00:00:00.0') AS last_modified_date from Temp_Source")
  
  // Save as avro files
  avro_data.write.mode("overwrite").format(s"com.databricks.spark.${output_file_format}").save(args(1))   // Passing path as command line argument, we can also pass direct path as well

  // Stop Spark Context
  sc.stop()
    
}
