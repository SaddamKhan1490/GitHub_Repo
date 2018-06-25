-- Parse simple JSON:-

-- I/P : {"emp_id":100000101,"emp_name":"aaaaaaa","address":"H No 10, xyz street","dept":"technology","salary":1000000,"doj":"2020-01-01"} 
simple_json_dataframe = LOAD '/user/test/input_files_dir/*' using JsonLoader('emp_id:int,emp_name:chararray,address:chararray,dept:chararray,salary:int,doj:chararray');
rmf /user/test/output_files_dir/;
STORE simple_json_dataframe INTO '/user/test/output_files_dir/' using PigStorage ('|');

-- O/P : 
100000101|aaaaaaa|H No 10, xyz street|technology|1000000|2020-01-01



-- Parse nested JSON:-

-- I/P : {"emp_id":100000101,"emp_name":"aaaaaaa","location_continent":{"asia":5000,"america":3000,"europe":2000),"dept":"technology","salary":1000000,"doj":"2020-01-01"}

dataframe_temp = LOAD '/user/test/input_files_dir/*' using JsonLoader('emp_id:int,emp_name:chararray,location_continent:(asia:int,america:int,europe:int),dept:chararray,salary:int,doj:chararray');
nested_json_dataframe = foreach dataframe_temp generate emp_id,emp_name,flatten(location_continent), dept, salary, doj;
rmf /user/test/output_files_dir/;
STORE nested_json_dataframe INTO '/user/test/output_files_dir/' using PigStorage (',');

-- O/P : 
100000101|aaaaaaa|5000|3000|2000|technology|1000000|2020-01-01



-- Parse JSON with Array:-

-- I/P : {"emp_id":100000101,"emp_name":"aaaaaaa","location_ids":[1001, 2001, 3001],"dept":"technology","salary":1000000,"doj":"2020-01-01"}

dataframe_temp = LOAD '/user/test/input_files_dir/*' using JsonLoader('emp_id:int,emp_name:chararray,location_ids:{t:(i:int)},dept:chararray,salary:int,doj:chararray');
nested_array_json_dataframe = foreach dataframe_temp generate emp_id,emp_name, flatten(location_ids), dept, salary, doj;
rmf /user/test/output_files_dir/;
STORE nested_array_json_dataframe INTO '/user/test/output_files_dir/' using PigStorage ('|');

-- O/P : 
100000101|aaaaaaa|1001|technology|1000000|2020-01-01
100000101|aaaaaaa|2001|technology|1000000|2020-01-01
100000101|aaaaaaa|3001|technology|1000000|2020-01-01
