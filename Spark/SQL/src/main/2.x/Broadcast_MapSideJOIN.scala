/**
  * Created by Saddam Khan on 5/14/2018.
  */
  
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}


case class Employee(name_e:String, age:Int, depId: String)
case class Department(id: String, name_d: String)

object BroadcastVariable extends App {

  println("BroadCasting Variable...")
  
  val sparkConf = new SparkConf().setAppName("BroadCasting Variable").setMaster("local[*]")
  val sc = new SparkContext(sparkConf)
  val sqlContext = new org.apache.spark.sql.SQLContext(sc)
  import sqlContext.implicits._
  
  // Suppress console logs
  sc.setLogLevel("ERROR")
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)

  
  var commonWords = Array("a", "an", "the", "of", "at", "is", "am","are","this","that","at", "in", "or", "and", "or", "not", "be", "for", "to", "it")
  val commonWordsMap = collection.mutable.Map[String, Int]()
  for(word <- commonWords){
    commonWordsMap(word) = 1
  }
  
  // Broadcast RDD
  var commonWordsBC = sc.broadcast(commonWordsMap)
  println("Printing Value of Broadcast Variable: "+commonWordsBC.value)

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  println("BroadCasting DataFrame...")
   
  val employeesRDD = sc.parallelize(Seq(
    Employee("Mary", 33, "IT"),
    Employee("Paul", 45, "IT"),
    Employee("Peter", 26, "MKT"),
    Employee("Jon", 34, "MKT"),
    Employee("Sarah", 29, "IT"),
    Employee("Steve", 21, "Intern")
  ))
  
  val departmentsRDD = sc.parallelize(Seq(
    Department("IT", "IT  Department"),
    Department("MKT", "Marketing Department"),
    Department("FIN", "Finance & Controlling")
  ))

  // Create dataFrame
  val employeesDF = employeesRDD.toDF("name_e", "age", "depId")
  val departmentsDF = departmentsRDD.toDF("id","name_d")
  departmentsDF.persist()
  
  /*
  | sc.Broadcast id for variable broadcasting & broadcast is for dataFrame broadcasting and performing MapSide Join with other dataframe
  | mandatorily need to include "import org.apache.spark.sql.functions._" in order to use broadcast for dataFrame i.e. perfrom MapSideJoin by caching BroadCast DataFrame on each executor machine
   */
  
  // Broadcast dataFrame
  val df_broadcast = sc.broadcast(departmentsDF)
  
  // Perform MapJoin between two dataFrame
  val map_join = employeesDF.join(broadcast(departmentsDF),col("depId") === col("id"),"inner").select("id", "age", "depId")
  map_join.show()
 
  // Perform MapJoin between two dataFrame
  val temp_map_df = employeesDF.join(broadcast(departmentsDF), $"depId" === $"id", "inner").show()
  
  // Stop Spark Context
  sc.stop()
}
