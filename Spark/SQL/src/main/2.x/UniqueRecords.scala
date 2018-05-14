/**
  * Created by Saddam Khan on 5/14/2018.
  */
  
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{asc, col, desc}
import org.apache.log4j.{Level, Logger}

object UniqueRecords extends App {

  println("Let's try finding Unique records in Spark...")

  // Create Spark Session
  val sparkSession = SparkSession.builder.master("local[*]").appName("Unique Records").getOrCreate()
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

  // Finding unique records using groupBy & where clause
  println("Number of rows inside DataFrame Created using groupBy & where clause API..." + group_join_DF.where($"count"<2).count())
  group_join_DF.where($"count"<2).show()

  // Finding unique records using using dropDuplicates
  println("Number of rows inside DataFrame Created using dropDuplicate API..." + group_join_DF.dropDuplicates().count())
  group_join_DF.dropDuplicates().show()

  // Finding unique records using distinct
  println("Number of rows inside DataFrame Created using distinct API..." + group_join_DF.distinct().count())
  group_join_DF.distinct().show()
  
  // Stop Spark Session
  sparkSession.stop()

}
