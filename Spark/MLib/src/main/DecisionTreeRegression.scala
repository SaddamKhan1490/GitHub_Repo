/**
 * Date :-  02/08/16.
 * Author :- Vidya
 */

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.feature.VectorIndexer
import org.apache.spark.ml.regression.DecisionTreeRegressionModel
import org.apache.spark.ml.regression.DecisionTreeRegressor


object decisionTreeRegression {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("DecisionTreeRegression").setMaster("local[4]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    println("Start...")

     // If We are in Spark Cluster then povide HDFS Path instead of Local Path 
    val data = sqlContext.read.format("text").load("C:/Users/Lenovo/Desktop/sd.txt")

    // Automatically identify categorical features, and index them.
    // Here, we treat features with > 4 distinct values as continuous.
    val featureIndexer = new VectorIndexer().setInputCol("features").setOutputCol("indexedFeatures").setMaxCategories(4).fit(data)

    // Splitting the data into training and test sets (30% held out for testing)
    val Array(trainingData, testData) = data.randomSplit(Array(0.7, 0.3))

    // Training the DecisionTree model.
    val dt = new DecisionTreeRegressor().setLabelCol("label").setFeaturesCol("indexedFeatures")

    // Chaining indexer and tree in a Pipeline
    val pipeline = new Pipeline().setStages(Array(featureIndexer, dt))

    // Train model to runs the indexer.
    val model = pipeline.fit(trainingData)

    // Making predictions.
    val predictions = model.transform(testData)

    // Selecting example rows to display.
    predictions.select("prediction", "label", "features").show(5)

    // Selecting (prediction, true label) and Computing (test error).
    val evaluator = new RegressionEvaluator().setLabelCol("label").setPredictionCol("prediction").setMetricName("rmse")
    val rmse = evaluator.evaluate(predictions)
    println("Root Mean Squared Error (RMSE) on test data = " + rmse)

    val treeModel = model.stages(1).asInstanceOf[DecisionTreeRegressionModel]
    println("Learned regression tree model:\n" + treeModel.toDebugString)
  
    println("End...")

  }
}
