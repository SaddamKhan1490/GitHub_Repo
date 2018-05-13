/**
  * Created by Saddam Khan on 5/14/2018.
  */

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel
import org.apache.spark.api.java.JavaSparkContext
import org.apache.log4j.{Level, Logger}

object LineageGraph extends App{

/*  println("Lineage Graph using Spark 1.x")

  val sparkConf = new SparkConf().setAppName("Lineage Graph").setMaster("local[*]").set("spark.driver.allowMultipleContexts","true")
  val sc = new SparkContext(sparkConf)
  val sqlContext = new org.apache.spark.sql.SQLContext(sc)

  import sqlContext.implicits._

  // Suppress console logs     
  sc.setLogLevel("ERROR")
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)

  val a = sc.parallelize(Seq(1,2,3))
  val b = a.distinct()

  // DebugString on RDD
  val c = b.toDebugString(1)
  println(c)

  // DebugString on DataFrame
  val df =  b.toDF()
  df.join(df)
  df.rdd.toDebugString
  sc.stop()*/
  
  println("Lineage Graph using Spark 2.x")
  
  // Create Spark Session
  val sparkSession = SparkSession.builder.master("local").appName("Lineage Graph").getOrCreate()
  import sparkSession.implicits._
  
  // Suppress console logs
  sparkSession.sparkContext.setLogLevel("ERROR")
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)
  
  // Create dataFrame
  val data = sparkSession.read.text("C:\\Users\\Lenovo\\Desktop\\Java_Home.txt").as[String]
  
  // Query intermediate received data i.e. apply transformation 
  val words = data.flatMap(value => value.split("\\s+"))
  
  // Process intermediate received data i.e. apply action
  val groupedWords = words.groupByKey(_.toLowerCase)
  val counts = groupedWords.count()
  
  //Displaying elements of dataFrame
  counts.show()

  // Display Lineage Graph of DataFrame
  counts.explain(true)

  // Display Lineage Graph of RDD
  counts.rdd.toDebugString
  
  // Stop Spark Session
  sparkSession.stop()

}
