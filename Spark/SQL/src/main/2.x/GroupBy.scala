
/**
  * Created by Saddam Khan on 5/14/2018.
  */
  
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.log4j.{Level, Logger}

object GroupBy extends App {

  println("Let's perform GroupBy in Spark...")

  // Create Spark Session 
  val sparkSession = SparkSession.builder.master("local[*]").appName("GroupBy_Examples").getOrCreate()
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

  // Displaying content of dataFrame
  empDF.show()

  // Grouping dataFrame using implicits #We need to mandatorily include all the columns in groupby clause for which we need output dataFrame
  val implicits_grouped_DF=empDF.select("empno", "ename", "job", "mgr", "hiredate", "sal", "comm", "deptno").groupBy( $"ename".alias("group_emp_name"), $"sal".alias("group_emp_sal"), $"deptno".alias("group_emp_deptno")).count().alias("count_implicits")
  implicits_grouped_DF.show()

  // Grouping dataFrame using functions #We need to mandatorily include all the columns in groupby clause for which we need output dataFrame
  val fxn_grouped_DF=empDF.select("empno", "ename", "job", "mgr", "hiredate", "sal", "comm", "deptno").groupBy( $"ename".alias("group_emp_name")).count()
  fxn_grouped_DF.show()

  // Sorting grouped dataFrame using implicits
  val implicits_sort_grouped_DF=empDF.select("empno", "ename", "job", "mgr", "hiredate", "sal", "comm", "deptno").groupBy( $"ename".alias("group_emp_name"), $"sal".alias("group_emp_sal"), $"deptno".alias("group_emp_deptno")).count().alias("count_implicits").sort($"sal".asc_nulls_first, $"empno".desc_nulls_first)
  implicits_sort_grouped_DF.show()

  // Ordering grouped dataFrame using implicits
  val implicits_ord_grouped_DF=empDF.select("empno", "ename", "job", "mgr", "hiredate", "sal", "comm", "deptno").groupBy( $"ename".alias("group_emp_name"), $"sal".alias("group_emp_sal"), $"deptno".alias("group_emp_deptno")).count().alias("count_implicits").orderBy($"sal".asc_nulls_first, $"empno".desc_nulls_first)
  implicits_ord_grouped_DF.show()

  // Sorting grouped dataFrame using functions
  val fxn_sort_grouped_DF=empDF.select("empno", "ename", "job", "mgr", "hiredate", "sal", "comm", "deptno").groupBy( $"ename".alias("group_emp_name"), $"sal".alias("group_emp_sal"), $"deptno".alias("group_emp_deptno")).count().alias("count_implicits").sort(asc("sal"), desc("empno"))
  fxn_sort_grouped_DF.show()

  // Ordering grouped dataFrame using functions
  val fxn_ord_grouped_DF=empDF.select("empno", "ename", "job", "mgr", "hiredate", "sal", "comm", "deptno").groupBy( $"ename".alias("group_emp_name"), $"sal".alias("group_emp_sal"), $"deptno".alias("group_emp_deptno")).count().alias("count_implicits").orderBy(asc("sal"), desc("empno"))
  fxn_ord_grouped_DF.show()

  // Joining grouped dataframe using implicits
  val implicits_group_join_DF = empDF.join(implicits_grouped_DF, $"ename" === $"ename", "cross").select("empno","ename", "job", "mgr", "hiredate", "sal", "comm", "deptno", "group_emp_deptno", "group_emp_sal", "group_emp_name")
  implicits_group_join_DF.show()

  // Joining grouped dataframe using functions
  val group_join_DF = empDF.join(fxn_grouped_DF, col("ename") === col("ename"), "cross").select("empno","ename", "job", "mgr", "hiredate", "sal", "comm", "deptno", "group_emp_name", "count")
  group_join_DF.show()
  
  // Stop Spark Session
  sparkSession.stop()
}
