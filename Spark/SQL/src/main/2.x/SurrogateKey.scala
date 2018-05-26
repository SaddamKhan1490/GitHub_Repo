/**
  * Created by Saddam Khan on 5/24/2018.
  */


import org.apache.spark.rdd.RDD
import org.apache.spark.SparkConf
import org.apache.spark.sql._
import org.apache.spark.sql.types._
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.sql.functions._
import org.apache.spark.sql.functions.col
import org.apache.spark.sql.expressions._
import org.apache.log4j.{Level, Logger}

object SurrogateKey extends App{
  
    println("Append Surrogate Key Column to Existing DataFrame...")

  // Create Spark Session
    val spark: SparkSession = SparkSession.builder.master("local").appName("RDD to DataFrame").getOrCreate()
    val sc = spark.sparkContext                   // Just used to create test RDDs
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
  
  // Create new schema i.e. schema with index
  // Append "rowid" column of type Long
  val newSchema = StructType(tuplesDF.schema.fields ++ Array(StructField("rowid", LongType, false)))
  
  // Zip on RDD level
  val rddWithId = tuplesDF.rdd.zipWithIndex()
  
  // Convert back to DataFrame
  val dfZippedWithId =  spark.createDataFrame(rddWithId.map{ case (row, index) => Row.fromSeq(row.toSeq ++ Array(index))}, newSchema)
  
  // Show results
  dfZippedWithId.show

  // Generate Surrogate Key on tupleDF assumming starting index as 1000 
  val df = tuplesDF.withColumn("row_num", (row_number.over(Window.partitionBy(lit(1)).orderBy(lit(1))))+ 1000)
   
  // Show results
  df.show
  
  sc.stop()
  spark.stop()
  
}
