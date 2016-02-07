/**
 * Date :-  02/07/16.
 * Author :- Vidya
 */

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions._
import org.apache.spark._

object textSearch {
  def main(args: Array[String]) {
  
      val conf = new SparkConf().setAppName("App1").setMaster("local")
      val sc = new SparkContext(conf)
      
      println("Start...")
      
      val textFile = sc.textFile("C:/Users/Lenovo/Desktop/Dataset")
      
      // Create a DataFrame
      val df = textFile.toDF("line")
      
      val errors = df.filter(col("line").like("%ERROR%"))
       
      // Count all the error
      errors.count()
       
      
      // Count errors MySQL
      errors.filter(col("line").like("%MySQL%")).count()
       
      // Fetches MySQL errors as an array of strings
      errors.filter(col("line").like("%MySQL%")).collect()
      
      println("End...")
  }
}
