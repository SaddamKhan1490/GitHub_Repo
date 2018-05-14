/**
  * Created by Saddam Khan on 5/14/2018.
  */

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{asc, col, desc}
import org.apache.log4j.{Level, Logger}

object Sorting_Sort_OrderBy extends App {
  
  println("Let's perform Sorting in Spark using Sort & OrderBy...")

  // Create Spark Session
  val sparkSession = SparkSession.builder.master("local[*]").appName("Sorting_Sort_OrderBy").getOrCreate()
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

  // Displaying elements of dataFrame
  empDF.show()

  // Grouping data of dataFrame
  val grouped_DF=empDF.select("empno", "ename", "job", "mgr", "hiredate", "sal", "comm", "deptno").groupBy( $"ename".alias("group_emp_name")).count()

  // Joining grouped_DF with empDF based on employeeName
  val group_join_DF = empDF.join(grouped_DF, col("ename") === col("ename"), "cross").select("empno","ename", "job", "mgr", "hiredate", "sal", "comm", "deptno", "group_emp_name", "count")

  // Sorting in ascending order using implicits
  val implicit_asc_sort_DF=group_join_DF.sort($"sal".asc_nulls_first, $"empno".desc_nulls_last)
  implicit_asc_sort_DF.show()

  // Sorting in descending order using implicits
  val implicit_desc_sort_DF=group_join_DF.sort($"sal".desc_nulls_last, $"empno".desc_nulls_last)
  implicit_desc_sort_DF.show()

  // Ordering in ascending order using implicits
  val implicit_asc_ord_DF=group_join_DF.orderBy($"sal".asc_nulls_first, $"empno".desc_nulls_last)
  implicit_asc_ord_DF.show()

  // Ordering in descending order using implicits
  val implicit_desc_ord_DF=group_join_DF.orderBy($"sal".desc_nulls_last, $"empno".desc_nulls_last)
  implicit_desc_ord_DF.show()

  // Sorting in ascending order using functions
  val fxn_asc_sort_DF=group_join_DF.sort(asc("sal"),desc("empno"))
  fxn_asc_sort_DF.show()

  // Sorting in descending order using functions
  val fxn_desc_sort_DF=group_join_DF.sort(desc("sal"),desc("empno"))
  fxn_desc_sort_DF.show()

  // Ordering in ascending order using functions
  val fxn_asc_ord_DF=group_join_DF.orderBy(asc("sal"),desc("empno"))
  fxn_asc_ord_DF.show()

  // Ordering in descending order using functions
  val fxn_desc_ord_DF=group_join_DF.orderBy(desc("sal"),desc("empno"))
  fxn_desc_ord_DF.show()
  
  // Stop Spark Session
  sparkSession.stop()

}
