/**
 * Date :-  02/07/16.
 * Author :- Vidya
 */

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark._


object WordCount {
  def main(args: Array[String]) {
  
      val conf = new SparkConf().setAppName("App1").setMaster("local")
      val sc = new SparkContext(conf)
      println("Start")
      //If You are in Spark Cluster the povide HDFS Path instead of Local Path
      val textFile = sc.textFile("C:/Users/Lenovo/Desktop/Input_Dataset/Sample.txt") 
      val counts = textFile.flatMap(line => line.split(" "))
                 .map(word => (word, 1))
                 .reduceByKey(_ + _)
      //If You are in Spark Cluster the povide HDFS Path instead of Local Path
      counts.saveAsTextFile("C:/Users/Lenovo/Desktop/Output_Dataset/")
          
      println("End")
  }
}
