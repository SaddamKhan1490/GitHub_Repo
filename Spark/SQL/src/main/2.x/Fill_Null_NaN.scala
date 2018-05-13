/**
  * Created by Saddam Khan on 5/14/2018.
  */
  
import org.apache.spark.sql.functions._
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}

object Fill_Null_NaN extends App {

  println("Let's try filling Null & NaN records with our desired values in Spark...")

  //  val sparkSession = SparkSession.builder.master("local").appName("example").getOrCreate()
  //  import sparkSession.implicits._

  // Create Spark Context
  val sparkConf = new SparkConf().setAppName("Simple Application to replace Null & NaN Values...").setMaster("local[*]")
  val sc = new SparkContext(sparkConf)
  val sqlContext = new org.apache.spark.sql.SQLContext(sc)
  import sqlContext.implicits._

  // Suppress console logs
  sc.setLogLevel("ERROR")
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)

  // Creating Case Class and declaring data types
  case class Company(cName: String, cId: String, details: String)
  case class Employee(name: String, id: String, email: String, company: Company)

  // Create Sample Dataframe
  val e1 = Employee("n1", null, null, Company("c1", "1", "d1"))
  val e2 = Employee("n2", "2", "n2@c1.com", Company("c1", "1", "d1"))
  val e3 = Employee("n3", null, "n3@c1.com", Company("c1", "1", "d1"))
  val e4 = Employee("n4", "4", "n4@c2.com", Company("c2", "2", "d2"))
  val e5 = Employee("n5", null, null, Company("c2", "2", "d2"))
  val e6 = Employee("n6", "6", "n6@c2.com", Company("c2", "2", "d2"))
  val e7 = Employee("n7", "7", null, Company("c3", "3", "d3"))
  val e8 = Employee("n8", "8", "n8@c3.com", Company("c3", "3", "d3"))
  val employees = Seq(e1, e2, e3, e4, e5, e6, e7, e8)
  val df = sc.parallelize(employees).toDF

  // Displaying elements of dataFrame
  df.show()

  // Replacing Null or NaN values with our desired value # here : ["id" -> "xxxx"] && ["email" -> "xxxx@yyy.zzz"]
  val df1 = df.na.fill("xxxx", Seq("id")).na.fill("xxxx@yyy.zzz", Seq("email")).show()      // Fill Null w.r.t. to column
  //val map = Map("id" -> "xxxx", "email" -> "xxxx@yyy.zzz"); df.na.fill(map).show()        // Fill Null w.r.t. to column # Map is supported from Scala Version 2.11.++
  val colNames = Array("id", "email"); val df2 = df.na.fill("xxxx", colNames).show()        // Fill all Null with same value irrespective of column

  // Replacing Null values with 0 # works only with numeric values i.e. integer datatype
  df.withColumn("id", when($"id".isNull, "0").otherwise("1")).show

  // Function to replace null values with 0 & non-null values with 1 # workks with string datatype
  def filter_null(field : Any) : Int = field match {
    case null => 0
    case _    => 1
  }
  
  // Apply filter_null function over dataFrame for replacing null & non-null values
  val non_null_df = df.rdd.map(line => (line(0).toString,filter_null(line(1)),filter_null(line(2)),line(0).toString,line(0).toString,line(0).toString)).toDF("name","id","email","cName","cId","details")
  non_null_df.show()

  // Stop Spark Context
  sc.stop()
  
}
