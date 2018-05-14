/**
  * Created by Saddam Khan on 5/14/2018.
  */

import org.apache.spark.sql.{Dataset, SparkSession}
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.log4j.{Level, Logger}

object UnionDataFrame {

  def main(args: Array[String]): Unit = {

    println("Let's apply Union on Spark DataFrame...")

    // Create Spark Session
    val sparkSession = SparkSession.builder.master("local[*]").appName("Union").getOrCreate()
    import sparkSession.implicits._

    // Suppress console logs
    sparkSession.sparkContext.setLogLevel("ERROR")
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    // Construct dataFrame
    val df1 = sparkSession.read.option("header", "false").csv("C:\\Users\\Lenovo\\Desktop\\data.txt")
    df1.show()
    println("---------------------------------")

    // Construct dataFrame
    val df2 = sparkSession.read.option("header", "true").csv("C:\\Users\\Lenovo\\Desktop\\data.txt")
    df2.show()
    println("---------------------------------")

    // Combine two different dataFrames into one without Null Values
    val df_union = df1.union(df2)
    df_union.show()
    println("---------------------------------")

    // Combine two different dataFrames into one with Null Values
    val df_union_with_nulls = df1.unionAll(df2)
    df_union_with_nulls.show()
    println("---------------------------------")
    
    // Stop Spark Session
    sparkSession.stop()
    
  }

}
