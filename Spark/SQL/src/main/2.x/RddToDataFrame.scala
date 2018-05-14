/**
  * Created by Saddam Khan on 5/14/2018.
  */
 
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types._
import org.apache.log4j.{Level, Logger}

object RddToDataFrame {
  
 def main(args: Array[String]): Unit = {

    println("Let's convert RDD to DataFrame... ")

    // Create Spark Session
    val spark: SparkSession = SparkSession.builder.master("local").appName("RDD to DataFrame").getOrCreate()
    val sc = spark.sparkContext                   // Just used to create test RDDs

    // Suppress console logs
    sc.setLogLevel("ERROR")
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)
  
    // Construct RDD
    val rdd = sc.parallelize(
      Seq(
            ("first", Array(2.0, 1.0, 2.1, 5.4)),
            ("test", Array(1.5, 0.5, 0.9, 3.7)),
            ("choose", Array(8.0, 2.9, 9.1, 2.5))
         )
        )

    // Construct dataFrame using schema generated using toDf()
    val dfWithSchema = spark.createDataFrame(rdd).toDF("id", "vals")
    
    // Display content of dataFrame
    dfWithSchema.show()

    val rowsRdd: RDD[Row] = sc.parallelize(
      Seq(
            Row("first", 2.0, 7.0),
            Row("second", 3.5, 2.5),
            Row("third", 7.0, 5.9)
          )
         )

    // Define schema & attach to RDD in order to construct dataFrame out of it
    val schema = new StructType().add(StructField("id", StringType, true)).add(StructField("val1", DoubleType, true)).add(StructField("val2", DoubleType, true))

    // Create dataFrame using schema generated using createDataFrame & structType.add()
    val df = spark.createDataFrame(rowsRdd, schema)
    
    // Display content of dataFrame
    df.show()

    // Create dataFrame using schema generated using createDataFrame & structType.array()
    val schemaRDD = new StructType(Array(StructField("id",StringType,nullable = true),StructField("val1",DoubleType,nullable = true),StructField("val2",DoubleType,nullable = true)))
    val aNamedDF = spark.createDataFrame(rowsRdd,schemaRDD)
    
    // Display content of dataFrame
    aNamedDF.show()
    
    // Stop Spark Context
    sc.stop()
    
    // Stop Spark Session
    spark.stop()

  }

}
