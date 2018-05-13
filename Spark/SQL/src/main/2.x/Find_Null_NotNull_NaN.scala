/**
  * Created by Saddam Khan on 5/14/2018.
  */
  
import BroadcastVariable.sc
import org.apache.spark.sql.functions._
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}

object Find_Null_NotNull_NaN extends App {


  println("Let's try finding Null_NotNull_NaN values in Spark...")

//  val sparkSession = SparkSession.builder.master("local").appName("example").getOrCreate()
//  import sparkSession.implicits._

  // Create Spark Context
  val sparkConf = new SparkConf().setAppName("Finding Null_NotNull_NaN values...").setMaster("local[*]")
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

  // Filtering Null Values

  //Using query
  df.filter("id is null").show()
  df.where("id is null").show()
  //Using Implicits
  df.filter($"id".isNull).show()
  df.where($"id".isNull).show()
  //Using functions
  df.filter(df.col("id").isNull).show()
  df.where(df.col("id").isNull).show()
  //Using dataFrame
  df.filter(df("id").isNull).show
  df.where(df("id").isNull).show


  // Filtering Non-Null Values

  //Using query
  df.filter("id is not null").show()
  df.where("id is not null").show()
  //Using Implicits
  df.filter($"id".isNotNull).show()
  df.where($"id".isNotNull).show()
  //Using functions
  df.filter(df.col("id").isNotNull).show()
  df.where(df.col("id").isNotNull).show()
  //Using dataFrame
  df.filter(df("id").isNotNull).show
  df.where(df("id").isNotNull).show

  // Filtering Not-Number Values

  //Using query
  df.filter("id is not number").show()
  df.where("id is not number").show()
  //Using Implicits
  df.filter($"id".isNaN).show()
  df.where($"id".isNaN).show()
  //Using functions
  df.filter(df.col("id").isNaN).show()
  df.where(df.col("id").isNaN).show()
  //Using dataFrame
  df.filter(df("id").isNaN).show
  df.where(df("id").isNaN).show

  // AND condition over multiple column of given dataFrame

  //Using query
  df.filter("id is not null and email is not null").show
  df.where("id is not null and email is not null").show
  //Using Implicits
  df.filter($"id".isNotNull && $"email".isNotNull).show
  df.where($"id".isNotNull && $"email".isNotNull).show
  //Using functions
  df.filter(col("id").isNotNull && col("email").isNotNull).show
  df.where(col("id").isNotNull && col("email").isNotNull).show
  //Using dataFrame
  df.filter(df("id").isNaN && df("email").isNaN).show
  df.where(df("id").isNaN && df("email").isNaN).show

  // OR condition over multiple column of given dataFrame

  //Using query
  df.filter("id is not null or email is not null").show
  df.where("id is not null or email is not null").show
  //Using Implicits
  df.filter($"id".isNotNull || $"email".isNotNull).show
  df.where($"id".isNotNull || $"email".isNotNull).show
  //Using functions
  df.filter(col("id").isNotNull || col("email").isNotNull).show
  df.where(col("id").isNotNull || col("email").isNotNull).show
  //Using dataFrame
  df.filter(df("id").isNaN || df("email").isNaN).show
  df.where(df("id").isNaN || df("email").isNaN).show


  // Dynamic filter condition i.e. useful when we don't want any column to have null value and there are large number of columns
  val filterCond = df.columns.map(x=>col(x).isNotNull).reduce(_ && _)
  val filteredDf = df.filter(filterCond)
  filteredDf.show
  
  // Stop Spark Context
  sc.stop()

}
