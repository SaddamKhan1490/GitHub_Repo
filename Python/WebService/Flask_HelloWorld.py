####################################################################################################################################################
#Developer    : Saddam                                                                                                                            #
#Date         : 2016/10/22                                                                                                                        #
#Description  : This script will create REST endpoint for HTTP request                                                                            #
####################################################################################################################################################


from flask import Flask
app = Flask(__name__)
@app.route("/")
def hello():
    return "Hello World!"
if __name__ == "__main__":
    app.run()
    
    
 # After running the above script switch to browser and hit "http://localhost:5000/" output will be printed i.e. "Hello World !"
