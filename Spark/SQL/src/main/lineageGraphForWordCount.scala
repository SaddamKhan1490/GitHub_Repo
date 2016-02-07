/**
 * Date :-  02/07/16.
 * Author :- Vidya
 */

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark._


object lineageGraph {
  def main(args: Array[String]) {
   
   val conf = new SparkConf().setAppName("Lineage-Demo").setMaster(args(0))
   val sc: SparkContext = new SparkContext(conf)
   
   //println("Start...")

   
   //If We are in Spark Cluster then povide HDFS Path instead of Local Path
   val Lines = sc.textFile("C:/Users/Lenovo/Desktop/Input_Dataset/Sample.txt") 
   println("Parent for Lines = "+ Lines.toDebugString() + " i.e.Here, Lines is HadoopBaseRDD" + "\n")

   val Split =  Lines.flatMap(word => (word,1))
   println("Parent for Split = "+ Split.toDebugString() )
   
   val Words = Lines.map(line=>line.split(" "))
   println("Parent for Words = "+ Words.toDebugString() )

   val Count =  Words.reduceByKey(_+_)
   println("Parent for Count = "+ Count.toDebugString() )
    
   Count.collect.foreach(println)
   
   println("End...")
 }
}
