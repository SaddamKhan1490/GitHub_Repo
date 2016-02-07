/**
 * Date :-  02/07/16.
 * Author :- Vidya
 */

    
  // A hive context adds support for finding tables in the MetaStore and writing queries using HiveQL. 
  // Users who do not have an existing Hive deployment can still create a HiveContext. 
  // When not configured by the hive-site.xml, the context automatically creates metastore_db and warehouse in the current directory.
  
  import org.apache.spark.SparkConf
  import org.apache.spark._
  import org.apache.spark.sql.SQLContext
  import org.apache.spark.sql.functions._
  import hiveContext.implicits._
  import hiveContext.sql
  import java.io.File
  import com.google.common.io.{ByteStreams, Files}
  import org.apache.log4j.{Level, Logger}

  object hiveOnSparkSQL {
      // Creating Case Class Records to hold (Key, Value) pair
      case class Record(key: Int, value: String)
        
      // Copy kv1.txt file from classpath to temporary directory
      val HStream = hiveOnSparkSQL.getClass.getResourceAsStream("C:/Users/Lenovo/Desktop/Dataset/Sample.txt")
      val HFile = File.createTempFile("HStream", "txt")
      HFile.deleteOnExit()
      ByteStreams.copy(HStream, Files.newOutputStreamSupplier(HFile))
    
      // Declaring Main()
      def main(args: Array[String]) {
        val sparkConf = new SparkConf().setAppName("hiveOnSparkSQL")
        val sc = new SparkContext(sparkConf)
        
        // Creating Hive SQL Context
        val hiveContext = new HiveContext(sc)
     
        println("Start...")
    
        sql("CREATE TABLE IF NOT EXISTS src (key INT, value STRING)")
        sql(s"LOAD DATA LOCAL INPATH '${HFile.getAbsolutePath}' INTO TABLE src")
    
        // Queries are expressed in HiveQL
        println("Result of 'SELECT *': ")
        sql("SELECT * FROM src").collect().foreach(println)
    
        // Aggregation queries are also supported.
        val count = sql("SELECT COUNT(*) FROM src").collect().head.getLong(0)
        println(s"COUNT(*): $count")
    
        // The results of SQL queries are themselves RDDs and support all normal RDD functions. 
        // Items in the RDD are of type Row, which allows you to access each column by ordinal.
        val rddFromSql = sql("SELECT key, value FROM src WHERE key < 10 ORDER BY key")
    
        println("Result of RDD.map:")
        val rddAsStrings = rddFromSql.map {
          case Row(key: Int, value: String) => s"Key: $key, Value: $value"
        }
    
        // We can also register RDDs as temporary tables within a HiveContext.
        val rdd = sc.parallelize((1 to 100).map(i => Record(i, s"val_$i")))
        rdd.toDF().registerTempTable("records")
    
        // Queries can then join RDD data with data stored in Hive.
        println("Result of SELECT *:")
        sql("SELECT * FROM records r JOIN src s ON r.key = s.key").collect().foreach(println)
    
        sc.stop()
        println("End...")
    
      }
    }
    
