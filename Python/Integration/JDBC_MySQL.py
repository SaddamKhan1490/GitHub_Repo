####################################################################################################################################################
#Developer    : Saddam                                                                                                                            #
#Date         : 2016/10/22                                                                                                                        #
#Description  : This script will create REST endpoint for HTTP request                                                                            #
####################################################################################################################################################

# Import required packages
import pymysql.cursors

# Connect to the database
connection = pymysql.connect(host='localhost',
                             user='user',
                             password='passwd',
                             db='db',
                             charset='utf8mb4',
                             cursorclass=pymysql.cursors.DictCursor)

try:
    with connection.cursor() as cursor:
        sql = "INSERT INTO `users` (`email`, `password`) VALUES (%s, %s)"           # Create a new record
        cursor.execute(sql, ('abc@gmail.com', 'very-secret'))                       # Execute above 

    connection.commit()                                                             # Establish connection to Database as connection is not autocommit by default


    with connection.cursor() as cursor:
        sql = "SELECT `id`, `password` FROM `users` WHERE `email`=%s"               # Read a single record
        cursor.execute(sql, ('abc@gmail.com',))                                     # Execute above 
        result = cursor.fetchone()
        print(result)
        
finally:
    connection.close()                                                              # Voluntarily closing connection
    
# Output : {'password': 'very-secret', 'id': 1}
