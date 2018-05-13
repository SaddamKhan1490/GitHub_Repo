/**
  * Created by Saddam Khan on 5/14/2018.
  */

import org.apache.spark.rdd.RDD
import org.apache.spark.SparkConf
import org.apache.spark.sql._
import org.apache.spark.sql.types._
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.functions.col
import org.apache.log4j.{Level, Logger}

object AppendColumnToDataFrame extends App {

  println("Append Column to Existing DataFrame...")

  // Create Spark Context
  val sparkConf = new SparkConf().setAppName("Simple Application to Append Column to an Existing DataFrame").setMaster("local[*]")
  val sc = new SparkContext(sparkConf)
  val sqlContext = new org.apache.spark.sql.SQLContext(sc)
  import sqlContext.implicits._

  // Suppress console logs
  sc.setLogLevel("ERROR")
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)
  
  // Create dataFrame from sequence i.e. array
  val test = sqlContext.createDataFrame(Seq( (4L, "spark i j k"), (5L, "l m n"), (6L, "mapreduce spark"), (7L, "apache hadoop"), (11L, "a b c d e spark"), (12L, "b d"), (13L, "spark f g h"), (14L, "hadoop mapreduce"))).toDF("id_seq", "text")
  test.show()

  val tuples = List((0L, 0.9), (4L, 3.0),(6L, 0.12), (7L, 0.7), (11L, 0.15), (12L, 6.1), (13L, 1.8))

  // Create RDD from tuple
  val rdd: RDD[(Long, Double)] = sc.parallelize((tuples.toSeq))
  
  // Create dataFrame from tuple
  val tuplesDF = tuples.toDF("id_tuple", "average")
  tuplesDF.show()

  // Append tuple to existing dataFrame using withColumn
  val tuplesDF_WithExtraColumn = tuplesDF.withColumn("average2", tuplesDF.col("average") + 10)
  tuplesDF_WithExtraColumn.show()

  // Append column to existing dataFrame using JOIN
  val joined = test.join(tuplesDF, col("id_seq") === col("id_tuple"), "inner").select("id_tuple", "text", "average")
  joined.show()

  // Append column to existing dataFrame using UDF & withColumn
  val func = udf((col1 : Double, col2 : Double) => { col1 + col2})
  val df1 = tuplesDF_WithExtraColumn.withColumn("newField", func(tuplesDF_WithExtraColumn("average"), tuplesDF_WithExtraColumn("average2")))
  df1.show()

  // Append column to existing dataFrame using UDF & Select
  val df2 = tuplesDF_WithExtraColumn.select($"*", func($"average",$"average2").as("newField"))
  df2.show()

  // Stop Spark Context
  sc.stop()
}
