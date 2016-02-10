/**
 * Date :-  02/10/16.
 * Author :- Vidya
 */

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object hdfsTextStreaming {
  def main(args: Array[String]) {
  
    // Checking the authentication credentials
    if (args.length < 1) {
      System.err.println("Usage: HdfsWordCount <directory>")
      System.exit(1)
    }
    
    StreamingExamples.setStreamingLogLevels()
    
    // Configuring & Creating spark streaming context to receive dstreams
    val sparkConf = new SparkConf().setAppName("HdfsTextStreaming")
    val ssc = new StreamingContext(sparkConf, Seconds(2))

    // Create the FileInputDStream on the directory and use the stream to count words in new files created
    // If We are in Spark Cluster then povide HDFS Path instead of Local Path
    val lines = ssc.textFileStream(args(0))
    val words = lines.flatMap(_.split(" "))
    val wordCounts = words.map(x => (x, 1)).reduceByKey(_ + _)
    wordCounts.print()
    
    ssc.start()
    ssc.awaitTermination()
  }
}
