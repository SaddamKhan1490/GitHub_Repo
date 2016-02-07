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
  import sqlContext.implicits._
  import org.apache.log4j.{Level, Logger}
  
  object RDDRelation {
  // Defining the schema of an RDD in order to make a Case Class with the desired column names and datatypes.
  case class Record(key: Int, value: String)

  // Declaring Main()
  def main(args: Array[String]) {
    val sparkConf = new SparkConf().setAppName("RDDRelation")
    val sc = new SparkContext(sparkConf)
  
    // Creating Hive SQL Context
    val sqlContext = new SQLContext(sc)
    
    println("Start...")

    // Creating RDD. Here we can also create RDD by loading data externally i.e. from files.
    val df = sc.parallelize((1 to 100).map(i => Record(i, s"val_$i"))).toDF()
    // Any RDD containing Case Classes can be registered as a table. And, the schema of the table is automatically inferred using scala reflection.
    df.registerTempTable("records")

    // Once tables have been registered, we can run SQL queries over them.
    println("Result of SELECT *:")
    sqlContext.sql("SELECT * FROM records").collect().foreach(println)

    // Aggregation queries are also supported.
    val count = sqlContext.sql("SELECT COUNT(*) FROM records").collect().head.getLong(0)
    println(s"COUNT(*): $count")

    // The results of SQL queries are themselves RDDs and support all normal RDD functions.
    // Items in the RDD are of type Row, which allows you to access each column by ordinal.
    val rddFromSql = sqlContext.sql("SELECT key, value FROM records WHERE key < 10")

    println("Result of RDD.map:")
    rddFromSql.map(row => s"Key: ${row(0)}, Value: ${row(1)}").collect().foreach(println)

    // Queries can also be written using a LINQ-like Scala DSL.
    df.where($"key" === 1).orderBy($"value".asc).select($"key").collect().foreach(println)

    // Write out an RDD as a parquet file.
    df.write.parquet("pair.parquet")

    // Read in parquet file.  Parquet files are self-describing so the schmema is preserved.
    //If You are in Spark Cluster the povide HDFS Path instead of Local Path
    val parquetFile = sqlContext.read.parquet("C:/Users/Lenovo/Desktop/Dataset/pair.parquet")

    // Queries can be run using the DSL on parequet files just like the original RDD.
    parquetFile.where($"key" === 1).select($"value".as("a")).collect().foreach(println)

    // These files can also be registered as tables.
    parquetFile.registerTempTable("parquetFile")
    sqlContext.sql("SELECT * FROM parquetFile").collect().foreach(println)

    sc.stop()
    println("End...")

  }
}
