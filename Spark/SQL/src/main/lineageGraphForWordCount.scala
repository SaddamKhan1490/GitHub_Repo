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

   val Lines = sc.textFile(args(1))
   println("Parent for Lines = "+ Lines.toDebugString() )

   val Split =  Lines.flatMap(word => (word,1))
   println("Parent for wordMap = "+ Split.toDebugString() )
   
   val Words = Lines.map(line=>line.split(" "))
   println("Parent for wordRDD = "+ Words.toDebugString() )

   val Count =  Words.reduceByKey(_+_)
   println("Parent for wordCount = "+ Count.toDebugString() )
    
   Count.collect.foreach(println)
   
   println("End...")
 }
}
