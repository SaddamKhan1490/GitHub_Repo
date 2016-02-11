/**
 * Date :-  02/11/16.
 * Author :- Vidya
 */

  #Importing required Libraries
  from pyspark.sql import *
  #from pyspark.sql import SQLContext, Row, StructField, StructType, IntegerType, StringType, DateType, datetime, MapType
  from pyspark import *#SparkContext, SparkConf 
  #from pyspark import itertools, sys
  import sys
  #fetching System Date
  import datetime,time
  
  #Declaring Application Name & Creating Spark Context object 
  conf = SparkConf().setAppName("appName").setMaster("local")
  sc = SparkContext(conf=conf)
  
  # sc is an existing SparkContext.
  sqlContext = SQLContext(sc)
  
  # Load a text file and convert each line to a tuple.
  lines = sc.textFile("/home/cloudera/Raw_Data/pple.txt")
  parts = lines.map(lambda l: l.split(","))
  people = parts.map(lambda p: (p[0], p[1], p[2], p[3], p[4], p[5].strip()))
  
  # The schema is encoded in a string.
  schemaString = "id name age j_date e_date salary"
  
  fields = [StructField(field_name, StringType(), True) for field_name in schemaString.split()]
  schema = StructType(fields)
  
  # Apply the schema to the RDD.
  schemaPeople = sqlContext.applySchema(people, schema)
  
  # Register the SchemaRDD as a table.
  schemaPeople.registerTempTable("people")
  
  #Executing query to display all records of employee table
  employee=sqlContext.sql("SELECT * from people")
  employeeName=employee.map(lambda p:  p[0] + p[1] + p[2] + p[3] + p[4] + p[5] +"\n\n\n\n")
  #Saving the output in a file Before generating Sequence Number
  employeeName.saveAsTextFile("/home/cloudera/Raw_Data/people.New")
  
  # Generating Surrogate Key & Displaying the result of above query
  for i,v in enumerate(employeeName.collect()):
       print i+1, "\t\t", v[0],v[1],v[2],v[3],v[4],v[5],"\n\n\n"
  
  #Writing the output in a file along with Surrogate Key
  orig_stdout = sys.stdout
  f = file('output.txt', 'w')
  sys.stdout = f
  
  #Displaying OutPut of Employee Table along with Surrogate Key
  for i,v in enumerate(employeeName.collect()):
       print i+1, "\t\t", v[0], "\n\n"
  
  sys.stdout = orig_stdout
  f.close()
  #End of Writing on File
  
  print "\n\n\nCongratz, ALL TASKS COMPLETED SUCCESSFULLY!!!\n\n\n"
