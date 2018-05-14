/**
  * Created by Saddam Khan on 5/14/2018.
  */
  
import org.apache.spark.sql.{Dataset, SparkSession}
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.log4j.{Level, Logger}

object RePartitioning_Coalesce {

  def main(args: Array[String]): Unit = {

    println("Let's try to apply RePartitioning_Coalesce on Spark DataFrame to File...")

    // Create Spark Session
    val sparkSession = SparkSession.builder.master("local").appName("RePartitioning_Coalesce").getOrCreate()
    import sparkSession.implicits._

    // Suppress console logs     
    sparkSession.sparkContext.setLogLevel("ERROR")
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    // Construct first dataFrame
    val df1 = sparkSession.read.option("header", "false").csv("C:\\Users\\Lenovo\\Desktop\\data.txt")
    df1.show()
    println("---------------------------------")

    // Construct second dataFrame
    val df2 = sparkSession.read.option("header", "true").csv("C:\\Users\\Lenovo\\Desktop\\data.txt")
    df2.show()
    println("---------------------------------")

    // Combine two dataFrame # Note : Union can only be applied in case both the input dataFrame have exactly same meta-data
    val df_union = df1.union(df2)
    df_union.show()
    println("---------------------------------")

    // Save output to directory using coalesce
    df_union.coalesce(1)
      .write.format("com.databricks.spark.csv")                        // Here we can save the output in other formats as well i.e. Json, Xml, Parquet, etc...
      .option("header", "true")
      .save("C:\\Users\\Lenovo\\Desktop\\mydata_coaesce.csv")

    // Save output to directory using repartitioning
    df_union.repartition(1)
      .write.format("com.databricks.spark.csv")                       // Here we can save the output in other formats as well i.e. Json, Xml, Parquet, etc...
      .option("header", "true")
      .save("C:\\Users\\Lenovo\\Desktop\\mydata_repartition.csv")    
      
    // Stop Spark Session
    sparkSession.stop()
    
  }

}
