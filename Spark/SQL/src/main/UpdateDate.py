/**
 * Date :-  02/11/16.
 * Author :- Vidya
 */

  # Importing required Libraries
  from pyspark.sql import *
  # from pyspark.sql import SQLContext, Row, StructField, StructType, IntegerType, StringType, DateType, datetime, MapType
  from pyspark import *#SparkContext, SparkConf 
  # fetching sys
  import sys
  # fetching System Date
  import datetime,time
  
  # Declaring Function for Displaying Current System Date & Time & auto generating a file with Current Date & Time
  def timeStamped(fname, fmt='%Y-%m-%d-%H-%M-%S_{fname}'):
      return datetime.datetime.now().strftime(fmt).format(fname=fname)
  
  with open(timeStamped('myfile.txt'),'w') as outf:
      outf.write('Succesfully Displayed the SYSTEM date!')
  # End  of Date & Time Function
  
  # Declaring Application Name & Creating Spark Context object 
  conf = SparkConf().setAppName("appName").setMaster("local")
  sc = SparkContext(conf=conf)
  
  # sc is an existing SparkContext.
  sqlContext = SQLContext(sc)
  
  # Load a text file and convert each line to a tuple.
  lines = sc.textFile("/home/cloudera/Raw_Data/pple.txt")
  parts = lines.map(lambda l: l.split(","))
  people = parts.map(lambda p: (p[0], p[1], p[2], p[3], p[4], p[5].strip()))
  
  # Declaring Schema:-The schema is encoded in a string.
  schemaString = "id name age j_date e_date salary"
  
  # Storing The Schema
  fields = [StructField(field_name, StringType(), True) for field_name in schemaString.split()]
  schema = StructType(fields)
  
  # Apply the schema to the RDD.
  schemaPeople = sqlContext.applySchema(people, schema)
  
  # Register the SchemaRDD as a table.
  schemaPeople.registerTempTable("people")
  
  # SQL can be run over SchemaRDDs that have been registered as a table.
  results = sqlContext.sql("SELECT id,name,age,j_date,e_date,salary FROM people ORDER BY age LIMIT 5")
  
  # Saving The Result in Parquet
  results.saveAsParquetFile("a")
  parquetFile1 = sqlContext.parquetFile("a")
  parquetFile1.registerTempTable("parquetFile1");
  
  # The results of SQL queries are RDDs and support all the normal RDD operations.
  names = results.map(lambda p: "Name:---> "  + p.name  + "\t" +"Age:---> " + p.age + "\t" + "Joining Date: " + p.j_date + "\t" + "End Date: " + p.e_date + "Salary: "+"\t\t" + p.salary+"\n\n\n\n\n")
  
  # Querying The Data within the Same Field via SQL Query to get the records with same ID & Different Salary!
  ults=sqlContext.sql("Select e1.id,e1.name,e1.age,e1.j_date,e1.salary from people e1,parquetFile1 e2 where e1.id=e2.id AND e1.salary>e2.salary")      
  #(select e2.salary from people e2 where e2.id=e1.id)")
  
  # The results of SQL queries are RDDs and support all the normal RDD operations.
  teenNames1 = ults.map(lambda p: "Id: " + p.id  + "\t\t"+"Name: " + p.name +"\t\t"+ "Age: " + p.age + "\t\t"+"Salary" + p.salary +"\n\n" )
  
  # Displaying the result of above query & along with Sequence Number
  for i,teenName1 in enumerate(teenNames1.collect()):
   print i+1,"\t\t",teenName1,"\n\n"
  
  # Function to populate start_date & end_date
  
  def generateDate(p):
          temp=p[0].split(",")
          if str(temp[3])=="null" or str(temp[3]) == "" or str(temp[3]) == " ":
                start_date=datetime.now()
          else:
                start_date=parseDateString(str(temp[3]))
  
          if (temp[4]) =="null" or str(temp[4]) == "" or str(temp[4]) == " ":
                end_date="01-01-100000"
          else:
                end_date=str(temp[4])
          return (str(start_date),str(end_date))
  
  # End of generateDate Function
  
  # Function to Compare & Update Date
  
  def updateDate(p,q):
          temp1=p[0].split(",")
          temp2=q[0].split(",")
          if str(temp1[0])<str(temp2[0]):
                  if str(temp1[3])<str(temp2[3]):
                          temp1[4]=temp2[3]
                          end_date=str(temp1[4])
                          print "\n\nSuccessfully Updated Record\n"
                  else:
                          print "\n\nNO updates on Salary for Employee ID: ", str(temp1[0])
          else:
               print "\n\nNO Matching Record found\n"
          return
  
  # End of updateDate Function
