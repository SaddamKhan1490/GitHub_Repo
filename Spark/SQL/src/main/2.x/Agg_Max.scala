/**
  * Created by Saddam Khan on 4/14/2018.
  */
  
import Agg_Min.sparkSession
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.Row


object Agg_Max extends App {

  println("Let's compute Max per column of spark dataFrame...")

  // Create Spark Session
  val sparkSession = SparkSession.builder.master("local").appName("Window Function").getOrCreate()
  import sparkSession.implicits._

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

  // Create dataFrame view
  val df = empDF

  // Max # treats 'inifinity' as max for numeric values & treats 'z' as max for alphabetical values
  df.agg(max(df(df.columns(0))).alias("max_empno"),max(df(df.columns(1))).alias("max_ename")).show
  df.agg(max("empno").alias("max_empno"), max("ename").alias("max_ename")).show()
  df.agg(max($"empno").alias("max_empno"), max($"ename").alias("max_ename")).show()
  df.agg(max(col("empno")).alias("max_empno"), max(col("ename")).alias("max_ename")).show()
  val Row(max_empno: Int, max_ename: String) = df.agg(max("empno"), max("ename")).head; println(max_empno+":"+max_ename)
  
  // Stop SparkSession context
  sparkSession.stop()

}
