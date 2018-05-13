/**
  * Created by Saddam Khan on 5/14/2018.
  */
  
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.functions._
import org.apache.log4j.{Level, Logger}


object JsonToDataFrame extends App{

  // Using SparkContext i.e. 1.x
  println("Converting JSON to DataFrame using SparkContext i.e. 1.x!")
  
  // Start Spark Context
  val sparkConf = new SparkConf().setAppName("Simple Parsing Nested JSON Application").setMaster("local[*]")
  val sc = new SparkContext(sparkConf)
  val sqlContext = new org.apache.spark.sql.SQLContext(sc)
  import sqlContext.implicits._
  
  // Suppress console logs     
  sc.setLogLevel("ERROR")
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)

  // A JSON dataset is pointed to by path.
  // The path can be either a single text file or a directory storing text files
  val path = "C:\\Users\\Lenovo\\Desktop\\JSON"       // We can pass in command line argument here i.e. try parameterizing 
  val peopleDF = sqlContext.read.json(path)
  peopleDF.printSchema()
  peopleDF.show()

  // Stop Spark Context
  sc.stop()
 
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  // Using SparkSession i.e. 2.x
  println("Converting JSON to DataFrame using SparkSession i.e. 2.x!")
  
  // Create Spark Session
  val sparkSession = SparkSession.builder.master("local").appName("example").getOrCreate()
  import sparkSession.implicits._
  
  // Suppress console logs
  sparkSession.sparkContext.setLogLevel("ERROR")
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)
      
  //val path = "C:\\Users\\Lenovo\\Desktop\\JSON"    // We can pass in command line argument here i.e. try parameterizing 
  val df = sparkSession.read.json(path)
  df.printSchema()
  df.show()

  // Accessing columns of the dataFrame which got created after loading Json File
  val temp = df.select("age", "city")
  temp.show()
  
  // Stop Spark Session
  sparkSession.stop()
}
