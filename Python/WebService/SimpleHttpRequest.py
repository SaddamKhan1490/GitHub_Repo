####################################################################################################################################################
#Developer    : Saddam                                                                                                                            #
#Date         : 2016/10/22                                                                                                                        #
#Description  : This script will create REST endpoint for HTTP request                                                                            #
####################################################################################################################################################

# Import required packages
from flask import Flask
from flask import jsonify
from flask import request

# Creates an app object from Flask
app = Flask(__name__)

# Create dictionary object which will act as in-memory database while retrieving records using web services
empDB=[
 {
 'id':'101',
 'name':'ABC',
 'title':'L1'
 },
 {
 'id':'102',
 'name':'XYZ',
 'title':'L2'
 }
 ]
 
# Create request URI response mapping for HTTP GET(i.e. select or retrive existing all record) method
@app.route('/empdb/employee',methods=['GET'])
def getAllEmp():
    return jsonify({'emps':empDB})

# Create request URI response mapping for HTTP GET(i.e. select or retrive existing record with specified id) method
@app.route('/empdb/employee/<empId>',methods=['GET'])
def getEmp(empId):
    usr = [ emp for emp in empDB if (emp['id'] == empId) ] 
    return jsonify({'emp':usr})

# Create request URI response mapping for HTTP PUT(i.e. update existing record with specified id) method
@app.route('/empdb/employee/<empId>',methods=['PUT'])
def updateEmp(empId):
    em = [ emp for emp in empDB if (emp['id'] == empId) ]
    if 'name' in request.json : 
        em[0]['name'] = request.json['name']
    if 'title' in request.json:
        em[0]['title'] = request.json['title']
    return jsonify({'emp':em[0]})

# Create request URI response mapping for HTTP POST(i.e. insert or create new record with specified id) method
@app.route('/empdb/employee',methods=['POST'])
def createEmp():
    dat = {
    'id':request.json['id'],
    'name':request.json['name'],
    'title':request.json['title']
    }
    empDB.append(dat)
    return jsonify(dat)

# Create request URI response mapping for HTTP DELETE(i.e. delete existing record with specified id) method
@app.route('/empdb/employee/<empId>',methods=['DELETE'])
def deleteEmp(empId):
    em = [ emp for emp in empDB if (emp['id'] == empId) ]
    if len(em) == 0:
       abort(404)
    empDB.remove(em[0])
    return jsonify({'response':'Success'})
 
# app.run() declared under main method starts the web server and ready to handle the HTTP request
if __name__ == '__main__':
 app.run()
 
 
# Note : Ways to access(send request & receive response) or verify service output 
        1> Terminal : curl -i http://localhost:5000/empdb
        2> Browser  : http://localhost:5000/empdb
