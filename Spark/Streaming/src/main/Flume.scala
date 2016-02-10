/**
 * Date :-  02/10/16.
 * Author :- Vidya
 */
 
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming._
import org.apache.spark.streaming.flume._
import org.apache.spark.util.IntParam

object flumeEventCount {
  def main(args: Array[String]) {
    // Checking the authentication credentials 
    if (args.length < 2) {
      System.err.println(
        "Usage: FlumeEventCount <host> <port>")
      System.exit(1)
    }

    StreamingExamples.setStreamingLogLevels()
    
    // Specifying the host & port to receive the request: <host> <port>
    val Array(host, IntParam(port)) = args.take(4)
    
    // Specifying the streaming batch interval
    val batchInterval = Milliseconds(2000)

    // Creating the context and setting the batch size
    val sparkConf = new SparkConf().setAppName("FlumeEventCount")
    val ssc = new StreamingContext(sparkConf, batchInterval)

    // Configuring & Creating Flume streaming context to receive dstreams a zeroMQ publisher should be running for receiving this stream.
    val stream = FlumeUtils.createStream(ssc, host, port, StorageLevel.MEMORY_ONLY_SER_2)

    // Printing out the count of events received from this server in each batch
    stream.count().map(cnt => "Received " + cnt + " flume events." ).print()

    ssc.start()
    ssc.awaitTermination()
  }
}
