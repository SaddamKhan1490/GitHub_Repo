

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.functions._

object Agg_Sum extends App {

  println("Let's compute Sum per column of spark dataFrame...")

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

  // Sum # treats '0' i.e. zero as min for numeric values & treats 'a' as min for alphabetical values
  df.agg(sum(df(df.columns(0))).alias("sum_empno"),sum(df(df.columns(3))).alias("sum_sal")).show
  df.agg(sum("empno").alias("sum_empno"), sum("sal").alias("sum_sal")).show()
  df.agg(sum($"empno").alias("sum_empno"), sum($"sal").alias("sum_sal")).show()
  df.agg(sum(col("empno")).alias("sum_empno"), sum(col("sal")).alias("sum_sal")).show()
  println("sum_empno:"+df.agg(sum("empno").as("sum_empno").cast("Int")).first.getInt(0))
  val summation: Int = df.agg(sum("empno").as("sum_empno").cast("Int")).first.getInt(0); println("sum_empno:"+summation)
  val sum_empno, sum_sal = df.agg(sum("empno").as("sum_col1").cast("Int"), sum("sal").as("sum_col2").cast("Int")).first.getInt(0); println("sum_empno:"+sum_empno+" sum_sal:"+ sum_sal)

  // Stop SparkSession context
  sparkSession.stop()

}
