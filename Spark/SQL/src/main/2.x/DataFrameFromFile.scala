/**
  * Created by Saddam Khan on 5/14/2018.
  */

import org.apache.spark.sql.{Dataset, SparkSession}
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.storage.StorageLevel
import org.apache.log4j.{Level, Logger}

object DataFrameFromFile {
  
  def main(args: Array[String]): Unit = {

    println("Let's create DataFrame from files...")

    // Create Spark Context
    val sparkSession = SparkSession.builder.master("local[*]").appName("Create dataFrame out of File...").getOrCreate()
    import sparkSession.implicits._
    val data = sparkSession.read.text("C:\\Users\\Lenovo\\Desktop\\data.txt").as[String]
    
    // Suppress console logs
    sparkSession.sparkContext.setLogLevel("ERROR")
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    // Create dataFrame out of textFile where entire file content is fit inside one single column
    val df = data.toDF("file")
    df.show()

    // Create dataFrame out of textFile using regex to generate schemaString
    val fileToDf = data.map(_.split(", ")).map{case Array(a,b,c) => (a.toInt,b,c.toInt)}.toDF("name","age","city")
    fileToDf.show()

    // Create dataFrame without header i.e. first line of file, out of textFile using .csv
    val df1 = sparkSession.read.option("header", "false").csv("C:\\Users\\Lenovo\\Desktop\\data.txt")
    df1.show()

    // Create dataFrame with header i.e. first line of file, out of textFile using .csv
    val df2 = sparkSession.read.option("header", "true").csv("C:\\Users\\Lenovo\\Desktop\\data.txt")
    df2.show()

    // Construct schema
    val schemaString = "id name age"
    val fields = schemaString.split(" ").map(fieldName => StructField(fieldName, StringType, nullable=true))
    val schema = StructType(fields)

    // Create dataFrame out of textFile using schemaString
    val dfWithSchema = sparkSession.read.option("header", "false").schema(schema).csv("C:\\Users\\Lenovo\\Desktop\\data.txt")
    dfWithSchema.show()

    // Create dataFrame with header & schema i.e. first line of file along with its dataType, out of textFile using .csv
    val df3 = sparkSession.read.option("header", "true").option("inferSchema", "true").csv("C:\\Users\\Lenovo\\Desktop\\data.txt")
    df3.show()

    // Create dataFrame with header & schema i.e. first line of file along with its dataType, out of textFile using .csv & droping few columns at the time of loading itself
    val df4 = sparkSession.read.option("header", "true").option("inferSchema", "true").csv("C:\\Users\\Lenovo\\Desktop\\data.txt").drop("1201")
    
    // Stop Spark Context
    sparkSession.stop()
    
  }
  
}
