/**
 * Date :-  02/08/16.
 * Author :- Saddam
 */

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.tree.RandomForest
import org.apache.spark.mllib.tree.model.RandomForestModel
import org.apache.spark.mllib.util.MLUtils
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint

object randomForest {
  
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("RandomForestRegressionExample").setMaster("local[4]")
    val sc = new SparkContext(conf)
    
    println("Start...")
    
    // If We are in Spark Cluster then povide HDFS Path instead of Local Path 
    val data = MLUtils.loadLibSVMFile(sc, "C:/Users/Lenovo/Desktop/sd.txt")

    // Split data into training (70%) and test (30%).
    val splits = data.randomSplit(Array(0.7, 0.3))
    val (trainingData, testData) = (splits(0), splits(1))

    val numClasses = 2
    val categoricalFeaturesInfo = Map[Int, Int]()
    val numTrees = 3 
    val featureSubsetStrategy = "auto" 
    val impurity = "variance"
    val maxDepth = 4
    val maxBins = 32
    
    // Training  Dataset
    val model = RandomForest.trainRegressor(trainingData, categoricalFeaturesInfo,
      numTrees, featureSubsetStrategy, impurity, maxDepth, maxBins)

    // Filtering & Predicting the Lables
    val labelsAndPredictions = testData.map { point =>
      val prediction = model.predict(point.features)
      (point.label, prediction)
    }
    val testMSE = labelsAndPredictions.map{ case(v, p) => math.pow((v - p), 2)}.mean()
    println("Test Mean Squared Error = " + testMSE)
    println("Learned regression forest model:\n" + model.toDebugString)
    
    // Save and load model
    // If We are in Spark Cluster then povide HDFS Path instead of Local Path
    model.save(sc, "target/tmp/myRandomForestRegressionModel")
    val sameModel = RandomForestModel.load(sc, "C:/Users/Lenovo/Desktop/Data")
    
    println("End...")
  }
}
