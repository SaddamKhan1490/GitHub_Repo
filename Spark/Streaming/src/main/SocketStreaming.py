/**
 * Date :-  02/11/16.
 * Author :- Vidya
 */

  from pyspark import SparkContext
  from pyspark.streaming import StreamingContext
  
  #Create a local StreamingContext with two working thread and batch interval of 1 second
  sc = SparkContext("local[2]", "NetworkWordCount")
  ssc = StreamingContext(sc, 1)
  
  # Create a DStream that will connect to hostname:port, like localhost:9999
  lines = ssc.socketTextStream("localhost", 9998)
  
  # Split each line into words
  words = lines.flatMap(lambda line: line.split(" "))
  
  # Count each word in each batch
  pairs = words.map(lambda word: (word, 1))
  wordCounts = pairs.reduceByKey(lambda x, y: x + y)
  
  # Print the first ten elements of each RDD generated in this DStream to the console
  wordCounts.print()
  
  # Saving The data to File in Local
  #val finalRecords: RDD[Iterable[EmployeeRecord]] = <result of a few computations and transformation>
  #finalRecords.flatMap(identity).saveAsTextFile("/home/cloudera/streaming")
  
  ssc.start()             # Start the computation
  ssc.awaitTermination()  # Wait for the computation to terminate
