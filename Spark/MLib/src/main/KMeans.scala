/**
 * Date :-  02/08/16.
 * Author :- Vidya
 */

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.ml.clustering.KMeans
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.sql.{DataFrame, SQLContext}
import scopt.OptionParser


object kMeansExample {

  def main(args: Array[String]): Unit = {
    // Creates a Spark context, SQL context & Set AppName via Scopt
    val conf = new SparkConf().setAppName(s"${this.getClass.getSimpleName}")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    println("Start...")
    
    // Creating a DataFrame
    val dataset: DataFrame = sqlContext.createDataFrame(Seq(
      (1, Vectors.dense(0.0, 0.0, 0.0)),
      (2, Vectors.dense(0.1, 0.1, 0.1)),
      (3, Vectors.dense(0.2, 0.2, 0.2)),
      (4, Vectors.dense(9.0, 9.0, 9.0)),
      (5, Vectors.dense(9.1, 9.1, 9.1)),
      (6, Vectors.dense(9.2, 9.2, 9.2))
    )).toDF("id", "features")

    // Training a k-means model
    val kmeans = new KMeans()
      .setK(2)
      .setFeaturesCol("features")
      .setPredictionCol("prediction")
    val model = kmeans.fit(dataset)

    // Showing the result
    println("Final Centers: ")
    model.clusterCenters.foreach(println)
    
    println("End...")
    
    sc.stop()
  }
}
