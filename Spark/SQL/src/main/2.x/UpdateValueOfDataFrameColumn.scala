/**
  * Created by Saddam Khan on 5/14/2018.
  */
  
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions.row_number
import org.apache.log4j.{Level, Logger}

object UpdateValueOfDataFrameColumn extends App {
  
  println("Let's update value of DataFrame column...")
  
  // Create Spark Session
  val sparkSession = SparkSession.builder.master("local[*]").appName("Update").getOrCreate()
  import sparkSession.implicits._
  
  // Suppress console logs     
  sparkSession.sparkContext.setLogLevel("ERROR")
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)

  // Create Sample History Dataframe
  val empDF = sparkSession.createDataFrame(Seq(
    (7369, "SMITH", "CLERK", 7902, "17-Dec-80", 800, 20, 10),
    (7499, "ALLEN", "SALESMAN", 7698, "20-Feb-81", 1600, 300, 30),
    (7521, "WARD", "SALESMAN", 7698, "22-Feb-81", 1250, 500, 30),
    (7566, "JONES", "MANAGER", 7839, "2-Apr-81", 2975, 0, 20),
    (7654, "MARTIN", "SALESMAN", 7698, "28-Sep-81", 1250, 1400, 30),
    (7698, "BLAKE", "MANAGER", 7839, "1-May-81", 2850, 0, 30),
    (7782, "CLARK", "MANAGER", 7839, "9-Jun-81", 2450, 0, 10),
    (7788, "SCOTT", "ANALYST", 7566, "19-Apr-87", 3000, 0, 20),
    (7839, "KING", "PRESIDENT", 0, "17-Nov-81", 5000, 0, 10),
    (7844, "TURNER", "SALESMAN", 7698, "8-Sep-81", 1500, 0, 30),
    (7876, "ADAMS", "CLERK", 7788, "23-May-87", 1100, 0, 20)
  )).toDF("empno", "ename", "job", "mgr", "hiredate", "sal", "comm", "deptno")

  // Create Sample Delta Dataframe
  val delta = sparkSession.createDataFrame(Seq(
    (7369, "SMITH", "CLERK", 7902, "17-Dec-80", 8000, 20, 10),
    (7499, "ALLEN", "SALESMAN", 7698, "20-Feb-81", 16000, 300, 30),
    (7521, "WARD", "SALESMAN", 7698, "22-Feb-81", 12500, 500, 30)
  )).toDF("empno", "ename", "job", "mgr", "hiredate", "sal", "comm", "deptno")

  // Combine both the dataframe
  val temp1 = empDF.union(delta)

  // Apply partitioning (Primary key Column i.e. column which is used maintain uniquiness and serve as base for identifying duplicate records) on dataframe & orderby column which holds the upated value i.e. timestamp, salary, etc...
  val partitionWindow = Window.partitionBy($"empno").orderBy($"sal".desc)

  // Apply row_number
  val rowNumberTest = row_number().over(partitionWindow)
  val temp2 = temp1.select($"*", rowNumberTest as "row_number")
  //temp1.select($"*", rowNumberTest as "row_number").show

  // Select updated record from list combined dataFrame bosed on row_number
  val update = temp2.where($"row_number"<2)
  update.show()

  // Stop Spark Session
  sparkSession.stop()
  
}
