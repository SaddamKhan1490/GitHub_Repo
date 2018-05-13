/**
  * Created by Saddam Khan on 5/14/2018.
  */
 
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}

case class Person_P(a: String, b: String, c: String, d: String, e: String, f: String, g: String, h: String, i: String, j: String, k: String , l: String, m: String, n: String, o: String, p: String, q: String, r: String, s: String, t: String, u: String, v: String)
// NOTE : We can have max of 22 fields until we use Scala Version 2.10, but can declare any number of fields inside given case class from Scala Version 2.11 onwards

object DataFrameUsingCaseClass extends App {
  
  
  println("Let's Create DataFrame using Case Class...")

  // Create Spark Context
  val sparkConf = new SparkConf().setAppName("Simple Application to Create DataFrame using Scala Case Class").setMaster("local[*]")
  val sc = new SparkContext(sparkConf)
  val sqlContext = new org.apache.spark.sql.SQLContext(sc)
  import sqlContext.implicits._

  // Suppress console logs
  sc.setLogLevel("ERROR")
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)
  
  // Create array object to store input data
  val data = Array(Person_P("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22"),
                   Person_P("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v")
                   )

  // Create an RDD from the raw data
  val RDD = sc.parallelize(data)

  // Print the RDD for debugging (this works, shows 2 dogs)
  RDD.collect().foreach(println)

  // Create a DataFrame from the RDD
  val DF = sqlContext.createDataFrame(RDD)

  // Print the DataFrame for debugging (this fails, shows 0 dogs)
  DF.show()

  // Stop Spark Context
  sc.stop()
  
}
