/**
  * Created by Saddam Khan on 5/14/2018.
  */
  
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}

object LoggerClass_LogLevels extends App{

  // Using SparkContext i.e. 1.x
  println("Suppressing Console Logs using SparkContext i.e. 1.x!")
  
  // Start Spark Context
  val sparkConf = new SparkConf().setAppName("Log Suppression 1.x").setMaster("local[*]").set("spark.streaming.receiver.writeAheadLog.enable", "true")
  val sc = new SparkContext(sparkConf)
  val sqlContext = new org.apache.spark.sql.SQLContext(sc)
  import sqlContext.implicits._
  
  // Suppress console logs     
  sc.setLogLevel("ERROR")
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)

  // Stop Spark Context
  sc.stop()
  
  // Using SparkSession i.e. 2.x
  println("Suppressing Console Logs using SparkSession i.e. 2.x!")
  
  // Create Spark Session
  val sparkSession = SparkSession.builder.master("local").appName("Log Suppression 2.x").getOrCreate()
  import sparkSession.implicits._
  
  // Suppress console logs
  sparkSession.sparkContext.setLogLevel("ERROR")
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)
  
  // Stop Spark Session
  sparkSession.stop()
}
