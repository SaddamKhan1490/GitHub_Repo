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
  
  #Reading data from file and Slitting it & Declaring Scehma to store the data of Employee Table
  emp = sc.textFile("/home/cloudera/Raw_Data/employee.csv")
  split = emp.map(lambda p: p.split(","));
  #people=parts.map(lambda p: Row(empId = p[0], firstName = p[1], lastName = p[2], email = p[3], salary  = p[4], managerId = p[5],departmentId = p[6]))
  empDetails=split.map(lambda p: Row(empId = int(p[0]), firstName = p[1], lastName = p[2], email = p[3], salary  = p[4], managerId = p[5],departmentId = p[6]))
  
  ##Reading data from file and Slitting it & Declaring Scehma to store the data of Department Table
  dept = sc.textFile("/home/cloudera/Raw_Data/department.csv")
  deptSplit = dept.map(lambda d: d.split("|"));
  deptDetails = deptSplit.map(lambda d: Row(departmentId = int(d[0]), departmentName = d[1], managerId = d[2], locationId = d[3]))
  
  #Registering ShemaRDD of Employee as a Temp Table
  schemaemployee = sqlContext.inferSchema(empDetails)
  schemaemployee.registerTempTable("employee")
  
  #Registering ShemaRDD of Department as a Temp Table
  schemaDepartment = sqlContext.inferSchema(deptDetails)
  schemaDepartment.registerTempTable("department")
  
  #Executing SQL Query over SchemaRDD that has been registered as a table
  employees = sqlContext.sql("select empId, firstName, lastName, email, salary, managerId, departmentId from employee")
  department = sqlContext.sql("select departmentId, departmentName, managerId, locationId from department")
  
  #Displaying Result of Executed SQL Query over SchemaRDD of Employee Table
  empDetails = employees.map(lambda p: "empId: " + str(p.empId) + " FirstName: " + p.firstName + " LastName: " + p.lastName  + " Email: " + p.email+ " salary: " + p.salary + " Manager Id: " + p.managerId + " Department ID: " + p.departmentId + "\n\n")
  
  for employee in empDetails.collect():
          print employee
  
  #Displaying Result of Executed SQL Query over SchemaRDD of Department Table
  deptDetail = department.map(lambda d: "Department ID: " + str(d.departmentId) + "Department Name:" + d.departmentName  +  " ManagerID: " + d.managerId + "Department Location: "+ d.locationId + "\n\n")
  
  for details in deptDetail.collect():
          print details
  
  #Executing JOIN Query over SchemaRDD that has been registered as a table
  empcount = sqlContext.sql("select count(empId) AS empCount, departmentId from employee group by departmentId")
  
  #Displaying Result of Executed SQL Query over SchemaRDD of JOIN Table
  empCountByDept = empcount.map(lambda e: "Department No: " + e.departmentId + " contains  " + str(e.empCount) + " Employees" + "\n\n")
  
  for i in empCountByDept.collect():
          print i
