/**
 * Date :-  02/07/16.
 * Author :- Vidya
 */
   
  import org.apache.spark.streaming.{Seconds, StreamingContext}
  import org.apache.spark.SparkContext._
  import org.apache.spark.streaming.twitter._
  import org.apache.spark.SparkConf
  
  /**
   * Calculating popular hashtags (topics) over sliding 10 and 60 second windows from a Twitter stream. 
   * The stream is instantiated with credentials and optionally filters supplied by the command line arguments.
   */
   
  object twitterPopularTags {
    def main(args: Array[String]) {
      
      // Checking the authentication credentials 
      if (args.length < 4) {
        System.err.println("Usage: TwitterPopularTags <consumer key> <consumer secret> " +
          "<access token> <access token secret> [<filters>]")
        System.exit(1)
      }
  
      StreamingExamples.setStreamingLogLevels()
  
      val Array(consumerKey, consumerSecret, accessToken, accessTokenSecret) = args.take(4)
      val filters = args.takeRight(args.length - 4)
  
      // Setting the system properties so that Twitter4j library used by twitter stream can use them to generat OAuth credentials
      System.setProperty("twitter4j.oauth.consumerKey", consumerKey)
      System.setProperty("twitter4j.oauth.consumerSecret", consumerSecret)
      System.setProperty("twitter4j.oauth.accessToken", accessToken)
      System.setProperty("twitter4j.oauth.accessTokenSecret", accessTokenSecret)
      
      // Configuring & Creating spark streaming context to receive twitter streams
      val sparkConf = new SparkConf().setAppName("TwitterPopularTags")
      val ssc = new StreamingContext(sparkConf, Seconds(2))
      val stream = TwitterUtils.createStream(ssc, None, filters)
      
      // Identifying the hashtags from the received dstreams
      val hashTags = stream.flatMap(status => status.getText.split(" ").filter(_.startsWith("#")))
      
      // Performing wordcount to sort to sort top 60  hashtags  
      val topCounts60 = hashTags.map((_, 1)).reduceByKeyAndWindow(_ + _, Seconds(60))
                       .map{case (topic, count) => (count, topic)}
                       .transform(_.sortByKey(false))
  
      // Performing wordcount to sort to sort top 10  hashtags  
      val topCounts10 = hashTags.map((_, 1)).reduceByKeyAndWindow(_ + _, Seconds(10))
                       .map{case (topic, count) => (count, topic)}
                       .transform(_.sortByKey(false))
  
  
      // Printing top 60 popular hashtags
      topCounts60.foreachRDD(rdd => {
                                      val topList = rdd.take(10)
                                      println("\nPopular topics in last 60 seconds (%s total):".format(rdd.count()))
                                      topList.foreach{case (count, tag) => println("%s (%s tweets)".format(tag, count))}
                                    })
      
      // Printing top 60 popular hashtags
      topCounts10.foreachRDD(rdd => {
                                      val topList = rdd.take(10)
                                      println("\nPopular topics in last 10 seconds (%s total):".format(rdd.count()))
                                      topList.foreach{case (count, tag) => println("%s (%s tweets)".format(tag, count))}
                                    })
  
      ssc.start()
      ssc.awaitTermination()
    }
  }
