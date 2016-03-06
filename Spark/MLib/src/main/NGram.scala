/**
 * Date :-  02/08/16.
 * Author :- Saddam
 */
 
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.ml.feature.NGram
import org.apache.spark.sql.SQLContext

object nGram {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("NGramExample").setMaster("local[4]")
    val sc = new SparkContext(conf)
    val sqlContext = new SQLContext(sc)

    println("Start...")
    
    // Creatin DataFrame
      val wordDataFrame = sqlContext.createDataFrame(Seq(
      (0, Array("Hi", "I", "heard", "about", "Spark")),
      (1, Array("I", "wish", "Java", "could", "use", "case", "classes")),
      (2, Array("Logistic", "regression", "models", "are", "neat"))
    )).toDF("label", "words")
    
    // Transforming the DataFrame to NGram
    val ngram = new NGram().setInputCol("words").setOutputCol("ngrams")
    val ngramDataFrame = ngram.transform(wordDataFrame)
    ngramDataFrame.take(3).map(_.getAs[Stream[String]]("ngrams").toList).foreach(println)
  
    println("End...")
    
    sc.stop()
  }
}
