/**
 * Date :-  02/10/16.
 * Author :- Vidya
 */
 
import kafka.serializer.StringDecoder
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.SparkConf

object kafkaStreamingWithSlidingWindow {
  def main(args: Array[String]) {
    // Checking the authentication credentials 
    if (args.length < 4) {
      System.err.println("Usage: KafkaWordCount <zkQuorum> <group> <topics> <numThreads>")
      System.exit(1)
    }

    StreamingExamples.setStreamingLogLevels()

    val Array(zkQuorum, group, topics, numThreads) = args.take(4)
    
    // Configuring & Creating spark streaming context to receive dstreams
    val sparkConf = new SparkConf().setAppName("KafkaStreamingWithSlidingWindow")
    val ssc = new StreamingContext(sparkConf, Seconds(2))
    
    // Checkpointing the Window to persist the DAG
    ssc.checkpoint("checkpoint")

    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
    val lines = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap).map(_._2)
    val words = lines.flatMap(_.split(" "))
    
    // Creating a window frame of 10 min & receiving the request at interval of every 2 sec
    val wordCounts = words.map(x => (x, 1L)).reduceByKeyAndWindow(_ + _, _ - _, Minutes(10), Seconds(2), 2)
    wordCounts.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
