/**
  * Created by Saddam Khan on 5/14/2018.
  */
  
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import org.apache.spark.storage.StorageLevel
import org.apache.spark.api.java.JavaSparkContext
import org.apache.log4j.{Level, Logger}

object PersistUnpersist extends App {

  println("Let's persist and unpersist DataFrame at different storage levels...")
  
  // Create Spark Session
  val sparkSession = SparkSession.builder.master("Persist-Unpersist RDD_DataFrame_HiveTable at different storage levels").appName("example").getOrCreate()
  import sparkSession.implicits._

  // Suppress console logs     
  sparkSession.sparkContext.setLogLevel("ERROR")
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)
  
  // Construct dataFrame
  val df = sparkSession.read.option("header", "true").option("inferSchema", "true").csv("C:\\Users\\Lenovo\\Desktop\\data.txt").drop("1201")

  // Persisting data at Memory & Disk Level , similarly we can persist RDD-DataFrame-DataSet-HiveTables at different memory level
  df.persist(StorageLevel.MEMORY_AND_DISK).explain(true)   // Persist & UnPersist at MemomoryOnly Level is equivalent to caching
  df.show()

  // Un-Persisting data at Memory & Disk Level
  df.unpersist().explain()
  
  // Display content of dataFrame
  df.show()

  // Verify Count
  df.count()
  
  // Stop Spark Session
  sparkSession.stop()

}
