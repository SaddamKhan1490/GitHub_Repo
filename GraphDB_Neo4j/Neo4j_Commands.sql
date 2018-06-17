
CREATE (n)																								// Create single Node
MATCH (n) return n 																						// Will return newly created Node
CREATE (n),(m),(o),(p),(q)                          													// CREATE multiple Node
Match (n) return n limit 5                          													// Will return first 5 Nodes

MATCH (n) WHERE id(n)=1 Return n                    													// Will search and return the node with id = 1
MATCH (n) WHERE id(n)=1 Return n                    													// Search using comparision <,<=,>,>=,<>
MATCH (n) WHERE id(n) IN [5,6,1] Return n           													// Search and return all the node where ID lies within given range 
MATCH (n) WHERE id(n)=1 DELETE n                    													// Delete the node whose internal id is 1
MATCH (n) WHERE id(n) IN [5,6,1] DELETE n           													// Delete all the node where ID lies within given range 
MATCH (n) DELETE n                                  													// Delete all the nodes in case there are NO existing relationships within given table
DELETE Entire Database: 
1> Stop neo4j server i.e. service neo4j stop; 2> Check status of neo4j server i.e. service neo4j status; 3> Delete the data directory i.e. rm -rf /var/lib/neo4j/data/*; 4> Start neo4j server again i.e. service neo4j start
LABEL (Used to group set of nodes togather):
1> Applied only on Nodes 2> Follow Camel-Casing to declare lable name 3> Cannot be applied to node which already has same lable
CREATE (n:Person)																						// CREATEd node with Lable
MATCH (n) WHERE n:Person Return n                   													// Search & Return all nodes with given Lable
CREATE (n:Person:Indian)																				// CREATEd node with multiple Lable
MATCH (n) WHERE n:Person:Indians Return n           													// Search & Return all nodes with both of given Lable
MATCH (n) WHERE n:Person OR n:Indians Return n      													// Search & Return all nodes with either of given Lable
MATCH (n) SET n:Person Return n                     													// Add label to all the existing nodes in database
MATCH (n) SET n:Person:India Return n               													// Add multiple label to all the existing nodes in database
MATCH (n) WHERE id(n)=1 SET n:Person Return n       													// Add label to specific node in given database
MATCH (n) WHERE id(n) IN[1,2] SET n:Person Return n 													// Add label to specified nodes in given database

MATCH (n) REMOVE n:Person Return n                  													// Remove Lables from Nodes NOT Nodes itself and must be used in conjunction with MATCH
MATCH (n) REMOVE n:Person:Indian Return n           													// Remove Multiple Lables from Nodes NOT Nodes itself and must be used in conjunction with MATCH
MATCH (n) WHERE id(n) IN [1,3,4] REMOVE n:Person Return n    											// Remove Lables from Specified Nodes NOT all Nodes and must be used in conjunction with MATCH
MATCH (n) WHERE id(n) IN [1,3,4] REMOVE n:Person SET n:Employee Return n    							// UPDATE Lables of Specified Nodes NOT all Nodes and must be used in conjunction with MATCH

MATCH (n) RETURN DISTINCT labels(n)																		// List ALL the Lables of DataBase
MATCH (n) WHERE id(n)=1 RETURN DISTINCT labels(n)						    							// List ALL the Lables of DataBase for specified ID
MATCH (n) RETURN DISTINCT count(labels(n))								    							// COUNT of ALL the Lables of DataBase
MATCH (n) RETURN DISTINCT count(labels(n)),labels(n)													// COUNT & List of ALL the Lables of DataBase
MATCH (n) WHERE n:Person:Indian DELETE(n)                                   							// Delete Node based on Lables

CREATE (x:Person{name:"ABC", age:27, salary:19500}) RETURN n																		// CREATE Node with specified Properties
CREATE (x:Person{name:"ABC", age:27, salary:19500, `Date of Birth`: "21-03-2013"}) RETURN n											// CREATE Node with Properties having Space
CREATE (x:Person{name:"ABC", age:27, salary:19500.00, `Date of Birth`: "21-03-2013", Address :["h1","h2"]}) RETURN n				// CREATE Node with Properties having Space & Multiple datatypes i.e. String, Int, Float, Array of String[DataTypes must be homogeneous for all values within given array], etc...

MATCH (n:Person{name:"ABC"}) Return n                                                                   // Return all the Nodes having Lable "Person & Property as "ABC"
MATCH (n:Person{Address :["h1","h2"]}) Return n															// Return all the Nodes having Lable "Person & Property as "Address :["h1","h2"]" i.e. an array and order of the nodes sequence also must be same
MATCH (n:Person) WHERE n.age > 25 AND (n.name="ABC" OR n.name="XYZ") Return n							// Logical Operator i.e. AND, OR, <>, >=, <=, etc...
MATCH (n:Person) WHERE toInt(n.age) > 25 Return n														// Type Conversion
MATCH (n:Person) WHERE n.age IN [25,27,28] Return n												    	// In case of string data will be case sensitive while performing comparision i.e. n.name IN ["ABC"] will NOT return result for n.name IN ["Abc"]
MATCH (n:Person) WHERE n.name="ABC" SET n.name="Abc" Return n											// Update the property of Node
MATCH (n:Person{name:"ABC"}) SET n.name="Abc" Return n													// Update name property of Node
MATCH (n:Person{name:"ABC"}) SET n.name="Abc", n.age="28", n.salary=20500 Return n						// Update name-age-salary property of Node
MATCH (n:Person{name:"ABC"}) SET n +={name:"Abc",age:"28",salary:20500} Return n						// Update name-age-salary property of Node
MATCH (n) WHERE n.name:"ABC" SET n:Lable_Name Return n													// Add Lables to Nodes Matching Properties
MATCH (gp{name:"ABC"}), (sl{name:"Abc"}) SET gp=sl Return gp,sl											// Copy all the data from one node to another

MATCH (n:Person{name:"ABC"}) SET n.salary=NULL Return n													// Delete Salary Property from Node
MATCH (n:Person) WHERE n.name="ABC" SET n.salary=NULL Return n											// Delete Salary Property from Node
MATCH (n) REMOVE n.name Return n																		// Delete Name Property from ALL Node
MATCH (n) WHERE n.salary<=20000 REMOVE n.name Return n													// Delete Name Property from ALL Node Where salary is less than 20000
MATCH (n) WHERE n.salary<=20000 DELETE n																// Delete ALL nodes whose Salary is less than 20000

==============================================================================================================================================================================================================
Tutorials Point:-
=================
 
CREATE NODES:-
--------------
CREATE (n)																								
MATCH (n) return n 																						

CREATE (n :user) return n
CREATE (Dhawan:person:player)
CREATE (Dhawan:player{name: "Shikar Dhawan", YOB: 1985, POB: "Hyderabad"}),(Rohit:player{name: "Rohit Sharma", YOB: 1985, POB: "Delhi"}) RETURN Dhawan,Rohit

CREATING RELATIONSHIPS-LABEL B/W NODES:
---------------------------------------
 A relationtion or lable can also have a property associated with it
 Relationships can be unidirectional or bi-directional
 There can exist 1-1, 1-N, N-N types of relationships
 
 CREATE (ja:Student {name:'Jack'})
 CREATE (jm:Student {name:'James'})
 MATCH (ja:Student {name:'Jack'}),(jm:Student {name:'James'}) RETURN ja,jm
 
 MATCH (ja:Student {name:'Jack'}),(jm:Student {name:'James'}) 
 CREATE (ja)-[r:FRIENDS {nickname:'Jimbo'}]->(jm)
 RETURN ja,jm
 
 MATCH (ja:Student {name:'Jack'}),(jm:Student {name:'James'}) 
 CREATE (ja)<-[r:FRIENDS {nickname:'Jimbo'}]-(jm)
 RETURN ja,jm
 
 MATCH (ja:Student {name:'Jack'}),(jm:Student {name:'James'}) 
 CREATE (ja)-[r:FRIENDS {nickname:'Jimbo'}]->(jm)
 CREATE (ja)<-[s:FRIENDS {nickname:'Jimbo'}]-(jm)
 RETURN ja,jm

QUERY and Find RELATIONSHIP between existing NODE:-
----------------------------------------------------

 MATCH (ja:Student {name:'Jack'})-[]->(jm:Student {name:'James'}) RETURN ja,jm
 MATCH (ja:Student {name:'Jack'})-[r:FRIENDS]->(jm:Student {name:'James'}) RETURN ja,jm
 MATCH (ja:Student {name:'Jack'})-[r:FRIENDS]-(jm:Student {name:'James'}) RETURN ja,jm

 MATCH (a:player), (b:Country) 
 WHERE a.name = "Shikar Dhawan" AND b.name = "India" 
 CREATE (a)-[r: BATSMAN_OF]->(b) 
 RETURN a,b 
 
 MATCH (a:player), (b:Country) 
 WHERE a.name = "Shikar Dhawan" AND b.name = "India" 
 CREATE (a)-[r:BATSMAN_OF {Matches:5, Avg:90.75}]->(b)  
 RETURN a,b 
 
CREATE PATH (CREATE NODE & RELATIONSHIP at same time) i.e. Nested Relationships:-
---------------------------------------------------------------------------------
 CREATE p=(Dhawan {name:"Shikhar Dhawan"})-[:TOP_SCORRER_OF]->(Ind{name:"India"})-[:WINNER_OF]->(CT2013{name:"Champions Trophy 2013"}) 
 RETURN p
 
MERGE i.e. CREATE & Merge:-
---------------------------
 CREATE (Dhawan:player{name: "Shikar Dhawan", YOB: 1985, POB: "Delhi"}) 
 CREATE (Ind:Country {name: "India"}) 
 CREATE (Dhawan)-[r:BATSMAN_OF]->(Ind) 

 MERGE (Jadeja:player) RETURN Jadeja 

 MERGE (CT2013:Tournament{name: "ICC Champions Trophy 2013"}) 
 RETURN CT2013, labels(CT2013)

 MERGE (Jadeja:player {name: "Ravindra Jadeja", YOB: 1988, POB: "NavagamGhed"}) 
 RETURN Jadeja 

 MERGE (Jadeja:player {name: "Ravindra Jadeja", YOB: 1988, POB: "NavagamGhed"}) 
 ON CREATE SET Jadeja.isCREATEd = "true" 
 ON MATCH SET Jadeja.isFound = "true" 
 RETURN Jadeja 

 MATCH (a:Country), (b:Tournament) 
   WHERE a.name = "India" AND b.name = "ICC Champions Trophy 2013" 
   MERGE (a)-[r:WINNERS_OF]->(b) 
 RETURN a, b 

SET (add property to existing node or relationship or can add or update existing property):-
--------------------------------------------------------------------------------------------
 CREATE (Dhawan:player{name: "shikar Dhawan", YOB: 1985, POB: "Delhi"}) 

 MATCH (Dhawan:player{name: "shikar Dhawan", YOB: 1985, POB: "Delhi"}) 
 SET Dhawan.highestscore = 187 
 RETURN Dhawan

 CREATE (Jadeja:player {name: "Ravindra Jadeja", YOB: 1988, POB: "NavagamGhed"})

 MATCH (Jadeja:player {name: "Ravindra Jadeja", YOB: 1988, POB: "NavagamGhed"}) 
 SET Jadeja.POB = NULL 
 RETURN Jadeja 

 MATCH (Jadeja:player {name: "Ravindra Jadeja", YOB: 1988})  
 SET Jadeja.POB="NavagamGhed",Jadeja.HS="90" 
 RETURN Jadeja

 CREATE (Anderson {name: "James Anderson", YOB: 1982, POB: "Burnely"})
 MATCH (Anderson {name: "James Anderson", YOB: 1982, POB: "Burnely"}) 
 SET Anderson: player 
 RETURN Anderson 

 CREATE (Ishant {name: "Ishant Sharma", YOB: 1988, POB: "Delhi"}) 
 MATCH (Ishant {name: "Ishant Sharma", YOB: 1988, POB: "Delhi"}) 
 SET Ishant: player:person 
 RETURN Ishant

DELETE :-
---------
 MATCH (n) DETACH DELETE n
 CREATE (Ishant:player {name: "Ishant Sharma", YOB: 1988, POB: "Delhi"}) 

 MATCH (Ishant:player {name: "Ishant Sharma", YOB: 1988, POB: "Delhi"}) 
 DETACH DELETE Ishant

 REMOVE (i.e. remove properties and labels from graph elements (Nodes or Relationships)):

 CREATE (Dhoni:player {name: "MahendraSingh Dhoni", YOB: 1981, POB: "Ranchi"})

 MATCH (Dhoni:player {name: "MahendraSingh Dhoni", YOB: 1981, POB: "Ranchi"}) 
 REMOVE Dhoni.POB 
 RETURN Dhoni 

 MATCH (Dhoni:player {name: "MahendraSingh Dhoni", YOB: 1981, POB: "Ranchi"}) 
 REMOVE Dhoni:player 
 RETURN Dhoni 

 CREATE (Ishant:player:person {name: "Ishant Sharma", YOB: 1988, POB: "Delhi"}) Return Ishant
 MATCH (Ishant:player:person {name: "Ishant Sharma", YOB: 1988, POB: "Delhi"}) 
 REMOVE Ishant:player:person 
 RETURN Ishant 

FOREACH (update data):-
-----------------------
 CREATE p = (Dhawan {name:"Shikar Dhawan"})-[:TOPSCORRER_OF]->(Ind{name: 
   "India"})-[:WINNER_OF]->(CT2013{name: "Champions Trophy 2013"}) 
 RETURN p 

 MATCH p = (Dhawan)-[*]->(CT2013) 
   WHERE Dhawan.name = "Shikar Dhawan" AND CT2013.name = "Champions Trophy 2013" 
 FOREACH (n IN nodes(p)| SET n.marked = TRUE)

MATCH (Retreive nodes):-
------------------------
 CREATE (Dhoni:player {name: "MahendraSingh Dhoni", YOB: 1981, POB: "Ranchi"}) 
 CREATE (Ind:Country {name: "India", result: "Winners"}) 
 CREATE (CT2013:Tornament {name: "ICC Champions Trophy 2013"}) 
 CREATE (Ind)-[r1:WINNERS_OF {NRR:0.938 ,pts:6}]->(CT2013) 

 CREATE(Dhoni)-[r2:CAPTAIN_OF]->(Ind)  
 CREATE (Dhawan:player{name: "shikar Dhawan", YOB: 1995, POB: "Delhi"}) 
 CREATE (Jadeja:player {name: "Ravindra Jadeja", YOB: 1988, POB: "NavagamGhed"})  

 CREATE (Dhawan)-[:TOP_SCORER_OF {Runs:363}]->(Ind) 
 CREATE (Jadeja)-[:HIGHEST_WICKET_TAKER_OF {Wickets:12}]->(Ind) 

 MATCH (n) RETURN n 
 MATCH (n:player) RETURN n 
 MATCH (Ind:Country {name: "India", result: "Winners"})<-[: TOP_SCORER_OF]-(n) RETURN n.name

OPTIONAL MATCH (Fill Null on missing part else same as MATCH):-
---------------------------------------------------------------
 MATCH (a:Tornament {name: "ICC Champions Trophy 2013"}) 
 OPTIONAL MATCH (a)-->(x) 
 RETURN x 

WHERE (Apply filter on MATCH Clause):-
--------------------------------------
 CREATE(Dhawan:player{name:"shikar Dhawan", YOB: 1985, runs:363, country: "India"})
 CREATE(Jonathan:player{name:"Jonathan Trott", YOB:1981, runs:229, country:"South Africa"})
 CREATE(Sangakkara:player{name:"Kumar Sangakkara", YOB:1977, runs:222, country:"Srilanka"})
 CREATE(Rohit:player{name:"Rohit Sharma", YOB: 1987, runs:177, country:"India"})
 CREATE(Virat:player{name:"Virat Kohli", YOB: 1988, runs:176, country:"India"})
 CREATE(Ind:Country {name: "India", result: "Winners"})

 MATCH (player)  
 WHERE player.country = "India" 
 RETURN player 

 MATCH (player)  
 WHERE player.country = "India" AND player.runs >=175 
 RETURN player 

 MATCH (n) 
 WHERE (n)-[: TOP_SCORER_OF]->( {name: "India", result: "Winners"}) 
 RETURN n 

RETURN (Return NODE, RELATIONSHIPS, and PROPERTIES):-
-----------------------------------------------------
 CREATE (Dhoni:player {name: "MahendraSingh Dhoni", YOB: 1981, POB: "Ranchi"}) 
 CREATE (Ind:Country {name: "India", result: "Winners"}) 
 CREATE (CT2013:Tornament {name: "ICC Champions Trophy 2013"}) 
 CREATE (Ind)-[r1:WINNERS_OF {NRR:0.938 ,pts:6}]->(CT2013) 
 CREATE(Dhoni)-[r2:CAPTAIN_OF]->(Ind) 

 CREATE (Dhoni:player {name: "MahendraSingh Dhoni", YOB: 1981, POB: "Ranchi"}) 
 RETURN Dhoni

 CREATE (Ind:Country {name: "India", result: "Winners"}) 
 CREATE (CT2013:Tornament {name: "ICC Champions Trophy 2013"}) 
 RETURN Ind, CT2013 

 CREATE (Ind)-[r1:WINNERS_OF {NRR:0.938 ,pts:6}]->(CT2013) 
 CREATE(Dhoni)-[r2:CAPTAIN_OF]->(Ind) 
 RETURN r1, r2 

 Match (Dhoni:player {name: "MahendraSingh Dhoni", YOB: 1981, POB: "Ranchi"}) 
 Return Dhoni.name, Dhoni.POB 

 Match p = (n {name: "India", result: "Winners"})-[r]-(x)  
 RETURN *

 Match (Dhoni:player {name: "MahendraSingh Dhoni", YOB: 1981, POB: "Ranchi"}) 
 Return Dhoni.POB as 'Place Of Birth'

ORDER BY (arrange the result data in order):-
---------------------------------------------
 CREATE(Dhawan:player{name:"shikar Dhawan", YOB: 1985, runs:363, country: "India"})
 CREATE(Jonathan:player{name:"Jonathan Trott", YOB:1981, runs:229, country:"South Africa"})
 CREATE(Sangakkara:player{name:"Kumar Sangakkara", YOB:1977, runs:222, country:"Srilanka"})
 CREATE(Rohit:player{name:"Rohit Sharma", YOB: 1987, runs:177, country:"India"})
 CREATE(Virat:player{name:"Virat Kohli", YOB: 1988, runs:176, country:"India"})

 MATCH (n)  
 RETURN n.name, n.runs 
 ORDER BY n.runs 

 MATCH (n) 
 RETURN n.name, n.runs, n.country 
 ORDER BY n.runs, n.country

 MATCH (n)  
 RETURN n.name, n.runs 
 ORDER BY n.runs DESC 

LIMIT (limit the number of rows in the output):-
------------------------------------------------
 MATCH (n)  
 RETURN n.name, n.runs 
 ORDER BY n.runs 
 LIMIT 3 

 MATCH (n)  
 RETURN n.name, n.runs 
 ORDER BY n.runs DESC 
 LIMIT 3 

 MATCH (n) 
 RETURN n.name, n.runs 
 ORDER BY n.runs DESC 
 LIMIT toInt(3 * rand())+ 1 

SKIP (Specify starting point of output and Skip all the previous rows):-
------------------------------------------------------------------------
 MATCH (n)  
 RETURN n.name, n.runs 
 ORDER BY n.runs DESC 
 SKIP 3 

 MATCH (n)  
 RETURN n.name, n.runs 
 ORDER BY n.runs DESC 
 SKIP toInt (2*rand())+ 1 

WITH & Collect (Chaining Query):-
---------------------------------
 MATCH (n) 
 WITH n 
 ORDER BY n.name DESC LIMIT 3 
 RETURN collect(n.name)

UNWIND (List to Rows):-
-----------------------
 UNWIND ['a', 'b', 'c', 'd'] AS x 
 RETURN x

 UNWIND [1,2,3,4] AS x 
 RETURN x

STRING (Apply String Transformation as we do in SQL):-
------------------------------------------------------
 MATCH (n:player)  
 RETURN UPPER(n.name), n.YOB, n.POB 

 MATCH (n:player)  
 RETURN LOWER(n.name), n.YOB, n.POB

 MATCH (n:player)  
 RETURN SUBSTRING(n.name,0,5), n.YOB, n.POB

 MATCH (n:model) 
 RETURN replace(n.name, ~'&.*;', '_');

AGGREGATE FUNCTION :-
---------------------

COUNT :-
--------
 MATCH(n{name: "India", result: "Winners"})--(x) RETURN n, count(*) 
 MATCH(n{name: "India", result: "Winners"})-[r]-(x) RETURN type (r), count(*) 

MAX :-
------ 
 MATCH (n:player) RETURN MAX(n.runs)

MIN :-
------
 MATCH (n:player) RETURN MIN(n.runs)

SUM :-
------
 MATCH (n:player) RETURN SUM(n.runs)

AVG :-
------
 MATCH (n:player) RETURN AVG(n.runs)

INDEX (Increase Performance):-
------------------------------
CREATE:-
--------
 CREATE INDEX ON:player(Dhawan) 

Delete:-
--------
 DROP INDEX ON:player(Dhawan) 

UNIQUE (Remove Duplicate):-
---------------------------
 CREATE CONSTRAINT ON (n:player) ASSERT n.id IS UNIQUE
 CREATE (Jadeja:player {id:002, name: "Ravindra Jadeja", YOB: 1988, POB: "NavagamGhed"})  // Verification 
 DROP CONSTRAINT ON (n:player) ASSERT n.id IS UNIQUE 

LOAD DATA from FILE :-
----------------------
 USING PERIODIC COMMIT 1000
 LOAD CSV WITH HEADERS FROM "https://raw.githubusercontent.com/neo4j/neo4j/2.3/manual/cypher/cypher-docs/src/docs/graphgists/import/persons.csv" AS csvLine
 CREATE (p:Person {id: toInt(csvLine.id), name: csvLine.name}) 
 RETURN count(*);

 LOAD CSV WITH HEADERS FROM "file:///C:/Users/S771125/Desktop/data.csv" AS csvLine
 CREATE (p:Person {id: toInt(csvLine.id), name: csvLine.name, age: csvLine.age}) 
 RETURN count(*);

Neo.ClientError.Statement.ExternalResourceFailed: Couldn't load the external resource at: file:/C:/Users/S771125/Documents/Softwares/NEO4J_HOME/import/Users/S771125/Desktop/data.csv
