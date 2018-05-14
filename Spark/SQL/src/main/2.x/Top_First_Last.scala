/**
  * Created by Saddam Khan on 5/14/2018.
  */
 
 import org.apache.spark.sql.SparkSession
import org.apache.log4j.{Level, Logger}

object Top_First_Last extends App {


  println("Let's try top-first-last function on spark dataFrame...")

  // Create Spark Session
  val sparkSession = SparkSession.builder.master("local[*]").appName("Top_First_Last").getOrCreate()
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

  // Create dataFrame view
  val df = empDF

  // Top r i.e. first elements as collection of list of rows
  df.sort( $"empno".asc).take(1).foreach(println)

  // First r i.e. first n-elements as collection of list of rows
  df.sort( $"empno".asc).take(1).foreach(println)

  // Last i.e. last n-elements as collection of list of rows
  df.sort( $"empno".desc).take(1).foreach(println)
    
  // Stop Spark Session
  sparkSession.stop()
  
}
