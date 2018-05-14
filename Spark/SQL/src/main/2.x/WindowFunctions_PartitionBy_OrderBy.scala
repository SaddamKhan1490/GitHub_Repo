/**
  * Created by Saddam Khan on 5/14/2018.
  */

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._
import org.apache.log4j.{Level, Logger}

object WindowFunctions_PartitionBy_OrderBy extends App {
  
  println("Let's apply Window Function on DataFrame...")

  // Create Spark Session
  val sparkSession = SparkSession.builder.master("local").appName("Window Functions").getOrCreate()
  import sparkSession.implicits._

  // Suppress console logs     
  sparkSession.sparkContext.setLogLevel("ERROR")
  Logger.getLogger("org").setLevel(Level.OFF)
  Logger.getLogger("akka").setLevel(Level.OFF)
  
  // Create Sample Dataframe
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

  // Use window with PartitionBy i.e. create partition window
  val partitionWindow = Window.partitionBy($"deptno").orderBy($"sal".desc)

  // Compute rank per department based on the salary
  val rankTest = rank().over(partitionWindow)
  empDF.select($"*", rankTest as "rank").show

  // Compute rank per department based on the salary
  val denseRankTest = dense_rank().over(partitionWindow)
  empDF.select($"*", denseRankTest as "dense_rank").show

  // Compute row_number per department based on the salary
  val rowNumberTest = row_number().over(partitionWindow)
  empDF.select($"*", rowNumberTest as "row_number").show

  // Compute running_sum per department based on the salary
  val sumTest = sum($"sal").over(partitionWindow)
  empDF.select($"*", sumTest as "running_total").show

  // Compute lead per department based on the salary
  val leadTest = lead($"sal", 1, 0).over(partitionWindow)
  empDF.select($"*", leadTest as "next_val").show

  // Compute lag per department based on the salary
  val lagTest = lag($"sal", 1, 0).over(partitionWindow)
  empDF.select($"*", lagTest as "prev_val").show

  // Compute first_value per department based on the salary
  val firstValTest = first($"sal").over(partitionWindow)
  empDF.select($"*", firstValTest as "first_val").show

  // Compute last_value per department based on the salary
  val lastValTest = last($"sal").over(partitionWindow)
  empDF.select($"*", lastValTest as "last_val").show

  // Compute last_value per department based on the salary by define new window partition to operate on row frame
  val partitionWindowWithUnboundedFollowing = Window.partitionBy($"deptno").orderBy($"sal".desc).rowsBetween(Window.currentRow, Window.unboundedFollowing)
  val lastValTest2 = last($"sal").over(partitionWindowWithUnboundedFollowing)
  empDF.select($"*", lastValTest2 as "last_val").show

  // Stop Spark Session
  sparkSession.stop()
  
}
