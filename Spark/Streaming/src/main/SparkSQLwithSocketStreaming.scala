/**
 * Date :-  02/10/16.
 * Author :- Vidya
 */
 
  import org.apache.spark.SparkConf
  import org.apache.spark.SparkContext
  import org.apache.spark.rdd.RDD
  import org.apache.spark.sql.SQLContext
  import org.apache.spark.storage.StorageLevel
  import org.apache.spark.streaming.{Seconds, StreamingContext, Time}
  import org.apache.spark.util.IntParam
  
object SqlNetworkWordCount {
    // Checking the authentication credentials 
  def main(args: Array[String]) {
    if (args.length < 2) {
      System.err.println("Usage: NetworkWordCount <hostname> <port>")
      System.exit(1)
    }

    StreamingExamples.setStreamingLogLevels()

    // Creating the context with a 2 second batch size
    val sparkConf = new SparkConf().setAppName("SqlNetworkWordCount")
    val ssc = new StreamingContext(sparkConf, Seconds(2))
    
    /* Configuring & Creating spark streaming context to receive dstreams from <hostname> <port>
     * Where, <hostname> and <port> describe the TCP server that Spark Streaming would connect to receive data.
    val lines = ssc.socketTextStream(args(0), args(1).toInt, StorageLevel.MEMORY_AND_DISK_SER)
    val words = lines.flatMap(_.split(" "))
    val pairs = words.map(word => (word, 1))
    val wordCounts = pairs.reduceByKey(_ + _)
    wordCounts.print()

    ssc.start()
    ssc.awaitTermination()
    
   /*    To run this on your local machine, you need to first run a Netcat server
    *    `$ nc -lk 9999`
    * and then run the example
    *    `$ bin/run-example classname_with_complete_path localhost 9999`
    */
  }
}
