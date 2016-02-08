/**
 * Date :-  02/08/16.
 * Author :- Vidya
 */

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.ml.classification.{BinaryLogisticRegressionSummary, LogisticRegression}
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.functions.max
import sqlCtx.implicits._


object logisticRegressionSummary {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("LogisticRegressionSummary").setMaster("local[4]")
    val sc = new SparkContext(conf)
    val sqlCtx = new SQLContext(sc)
    
    println("Start...")

    // Loading training Dataset
    // If We are in Spark Cluster then povide HDFS Path instead of Local Path 
    val training = sqlCtx.read.format("text").load("C:/Users/Lenovo/Desktop/sd.txt")

    val lr = new LogisticRegression().setMaxIter(10).setRegParam(0.3).setElasticNetParam(0.8)

    // Fitting the model
    val lrModel = lr.fit(training)

    // Extracting the summary from the returned LogisticRegressionModel 
    val trainingSummary = lrModel.summary

    // Obtaining the objective history per iteration.
    val objectiveHistory = trainingSummary.objectiveHistory
    objectiveHistory.foreach(loss => println(loss))

    // Obtaining the metrics using BinaryLogisticRegressionSummary to judge performance on test data.
    val binarySummary = trainingSummary.asInstanceOf[BinaryLogisticRegressionSummary]

    // Obtaining the receiver-operating characteristic as a Dataframe and areaUnderROC.
    val roc = binarySummary.roc
    roc.show()
    println(binarySummary.areaUnderROC)

    // Setting the model threshold to maximize F-Measure
    val fMeasure = binarySummary.fMeasureByThreshold
    val maxFMeasure = fMeasure.select(max("F-Measure")).head().getDouble(0)
    val bestThreshold = fMeasure.where($"F-Measure" === maxFMeasure).select("threshold").head().getDouble(0)
    lrModel.setThreshold(bestThreshold)
    
    println("End...")
    
    sc.stop()
  }
}
