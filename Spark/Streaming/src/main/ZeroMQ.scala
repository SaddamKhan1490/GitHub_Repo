/**
 * Date :-  02/10/16.
 * Author :- Vidya
 */

  import akka.actor.ActorSystem
  import akka.actor.actorRef2Scala
  import akka.util.ByteString
  import akka.zeromq._
  import akka.zeromq.Subscribe
  import org.apache.spark.streaming.zeromq._
  import com.typesafe.config.ConfigFactory
  import scala.language.implicitConversions
  import org.apache.spark.{SparkConf, TaskContext}
  import org.apache.spark.streaming.{Seconds, StreamingContext}
  
  object zeroMQ {
    def main(args: Array[String]) {
      // Checking the authentication credentials 
      if (args.length < 2) {
        System.err.println("Usage: ZeroMQWordCount <zeroMQurl> <topic>")
        System.exit(1)
      }
      StreamingExamples.setStreamingLogLevels()
      val Seq(url, topic) = args.toSeq
      val sparkConf = new SparkConf().setAppName("ZeroMQ")
      
      // Creating the context with a 2 second batch size
      val ssc = new StreamingContext(sparkConf, Seconds(2))
  
      def bytesToStringIterator(x: Seq[ByteString]): Iterator[String] = x.map(_.utf8String).iterator
  
      // Configuring & Creating ZeroMQ streaming context to receive dstreams a zeroMQ publisher should be running for receiving this stream.
      val lines = ZeroMQUtils.createStream( ssc, url, Subscribe(topic), bytesToStringIterator _)
      val words = lines.flatMap(_.split(" "))
      val wordCounts = words.map(x => (x, 1)).reduceByKey(_ + _)
      wordCounts.print()
      
      ssc.start()
      ssc.awaitTermination()
    }
  }
