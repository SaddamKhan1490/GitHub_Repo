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
      
      // Creating RDD
      val textFile = sc.textFile("C:/Users/Lenovo/Desktop/Dataset.txt")
      
      // Creating a DataFrame
      val df = textFile.toDF("line")
      
      // Matching the Search Pattern using Regex i.e. Regural Expressions
      val errors = df.filter(col("line").like("%ERROR%"))
       
      // Counting all the error
      errors.count()
      
      // Counting errors MySQL
      errors.filter(col("line").like("%ClassNotFound%")).count()
       
      // Fetching MySQL errors as an array of strings
      errors.filter(col("line").like("%ClassNotFound%")).collect()
      
      println("End...")
  }
}
