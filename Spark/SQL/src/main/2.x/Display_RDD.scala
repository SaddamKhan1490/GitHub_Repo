/**
  * Created by Saddam Khan on 5/14/2018.
  */
  
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}

object Display_RDD extends App{

  println("Basic methods to display output on console...")
  
  // Create Spark Context
  val sparkConf = new SparkConf().setAppName("BasicOperationsOnSparkRDD").setMaster("local[*]").set("spark.driver.allowMultipleContexts","true")
  val sc = new SparkContext(sparkConf)
  val sqlContext = new org.apache.spark.sql.SQLContext(sc)
  import sqlContext.implicits._
  
  // Suppress console logs
  sc.setLogLevel("ERROR")
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)

  // Create RDD
  val a = sc.parallelize(Seq(1,2,3))
  val b = a.distinct()
  val c = b.toDebugString(1)
  println("Printing Value of variable C:"+c)

  // Cache RDD for re-use
  a.cache()

  // Display output on console 
  a.collect().foreach(println)
  println("---------------------")
  a.take(3).foreach(println)
  println("---------------------")
  a.top(2)
  println("---------------------")
  a.takeOrdered(3).foreach(println)
  println("---------------------")
  a.takeOrdered(3)(Ordering[Int].reverse).foreach(println)
  println("---------------------")
  a.take(3).last
  
  // Stop Spark Context
  sc.stop()

}
