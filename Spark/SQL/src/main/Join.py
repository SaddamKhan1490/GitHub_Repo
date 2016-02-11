/**
 * Date :-  02/11/16.
 * Author :- Vidya
 */
 
  #Importing the required Libraries
  
  from pyspark.sql import SQLContext, Row
  from pyspark import SparkContext, SparkConf
  
  #Declaring Application Name & Creating Spark Context object 
  conf = SparkConf().setAppName("appName").setMaster("local")
  sc = SparkContext(conf=conf)
  
  #Creating SQLContext Object 
  sqlContext = SQLContext(sc)
  
  #Creating Table-1
  
  #Reading Data from file then Splitting it into words then declaring Schema & Storing it into the Schema
  line1=sc.textFile("/home/cloudera/Raw_Data/DOB1.txt")
  part1=line1.map(lambda l1: l1.split(","));
  people1=part1.map(lambda p1: Row(dob=p1[0], id=p1[1], name=p1[2]))
  
  #Registering the Schema in Table for performing SQL Operations 
  schemaPeople1=sqlContext.inferSchema(people1)
  schemaPeople1.registerTempTable("people1")
  
  #Executing Query
  teenager1=sqlContext.sql("SELECT name from people1")
  teenager1Name=teenager1.map(lambda p1: "Name:" + p1.name)
  
  #Saving result of above Query in a file
  teenager1Name.saveAsTextFile("people1")
  
  #Displaying the Result in Console Window
  for teen1 in teenager1Name.collect():
          print teen1
  
  
  #Table-2
  
  #Reading Data from file then Splitting it into words then declaring Schema & Storing it into the Schema
  line2=sc.textFile("/home/cloudera/Raw_Data/DOB2.txt")
  part2=line2.map(lambda l2: l2.split(","));
  people2=part2.map(lambda p2: Row(dob=p2[0], id=p2[1], name=p2[2]))
  
  #Registering the Schema in Table for performing SQL Operations 
  schemaPeople2=sqlContext.inferSchema(people2)
  schemaPeople2.registerTempTable("people2")
  
  #Executing Query
  teenager2=sqlContext.sql("SELECT name from people2")
  teenager2Name=teenager2.map(lambda p2: "Name:" + p2.name)
  
  #Saving result of above Query in a file
  teenager2Name.saveAsTextFile("people2")
  
  #Displaying the Result in Console Window
  for teen2 in teenager2Name.collect():
          print teen2
  
  #Join Condition
  
  #Executing Query
  join_result = sqlContext.sql("select p.name AS NAME_PEOPLE1, p.dob AS DOB_PEOPLE1, m.name AS NAME_PEOPLE2, m.dob AS DOB_PEOPLE2  FROM people1 AS p JOIN people2 AS m on p.dob = m.dob")
  join_out=join_result.map(lambda j: "Name-1:" + j.NAME_PEOPLE1 + "Date Of Birth-1:"+ j.DOB_PEOPLE1 +"\n\n"+"Name-2:" + j.NAME_PEOPLE2 + "Date Of Birth-2" + j.DOB_PEOPLE2+"\n\n")
  
  #Saving result of above Query in a file
  join_out.saveAsTextFile("Match_DOB")
  
  #Displaying the Result in Console Window
  for jn in join_out.collect():
          print jn
  
  
  
