/**
  * Created by Saddam Khan on 5/14/2018.
  */
  
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.log4j.{Level, Logger}

object Where_Fliter extends App {

  println("Let's try Filter & Where spark dataFrame...")

  // Create Spark Session
  val sparkSession = SparkSession.builder.master("local[*]").appName("Where_Filter").getOrCreate()
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

  // Create copy of dataFrame view
  val df = empDF

  // Create temporary object
  val items =  List(7369, 7499, 7566)

  // Apply Filter to select desired data
  df.filter($"empno".isin(items:_*)).show
  val temp1 = df.select($"empno").filter($"empno".isin(items:_*)).show()
  df.select($"empno").filter(e => e.getInt(0)%2==0).show()
  df.filter(not(substring(col("empno"), 0, 3).isin("765", "784"))).show()
  df.filter("empno not like '77%' and ename not like 'A%'").show
  val df1 = df.filter(not(df("empno").rlike("756"))&&not(df("ename").like("JON"))).show()
  val df2 = df.filter(not(df("empno").startsWith("756"))&&not(df("ename").endsWith("ES"))).show()

  // Apply Where to select desired data
  df.where($"empno".isin(items:_*)).show
  val temp2 = df.where($"empno".isin(items:_*)).show
  df.where(not(substring(col("empno"), 0, 3).isin("765", "784"))).show()
  df.where("empno not like '77%' and ename not like 'A%'").show
  //val df3 = df.Where_Fliter.super(not(df("c2").rlike("MSL"))&&not(df("c2").rlike("HCP")))      // Supported from Scala Version 2.11.++
  val df4 = df.where(not(df("empno").rlike("756"))&&not(df("ename").like("ES"))).show()
  val df5 = df.where(not(df("empno").startsWith("756"))&&not(df("ename").endsWith("ES"))).show()

  // Stop Spark Session
  sparkSession.stop()
}
