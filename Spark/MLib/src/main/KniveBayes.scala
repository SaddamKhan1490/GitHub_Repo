/**
 * Date :-  02/07/16.
 * Author :- Vidya
 */

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.classification.{NaiveBayes, NaiveBayesModel}
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint


object kniveBayes {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("NaiveBayesExample").setMaster("local[4]")
    val sc = new SparkContext(conf)
    
    println("Start...")
  
    //If We are in Spark Cluster then povide HDFS Path instead of Local Path 
    val data = sc.textFile("C:/Users/Lenovo/Desktop/sd.txt")
    val parsedData = data.map { line =>
                                val parts = line.split(',')
                                LabeledPoint(parts(0).toDouble, Vectors.dense(parts(1).split(' ').map(_.toDouble)))
                              }

    // Split data into training (60%) and test (40%).
    val splits = parsedData.randomSplit(Array(0.6, 0.4), seed = 11L)
    val training = splits(0)
    val test = splits(1)
    
    // Training  Dataset
    val model = NaiveBayes.train(training, lambda = 1.0, modelType = "multinomial")
    
    // Filtering & Predicting the Lables with Accuracy
    val predictionAndLabel = test.map(p => (model.predict(p.features), p.label))
    val accuracy = 1.0 * predictionAndLabel.filter(x => x._1 == x._2).count() / test.count()
    println(accuracy + "(Approx)")
    
    // Save and load model
    model.save(sc, "C:/Users/Lenovo/Desktop/Data/myNaiveBayesModel")
    val sameModel = NaiveBayesModel.load(sc, "C:/Users/Lenovo/Desktop/Data/myNaiveBayesModel")
    
    println("End...")
  }
}
