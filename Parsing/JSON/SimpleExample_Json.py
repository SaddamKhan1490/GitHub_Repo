#########################################################################################################################################################################
#Developer    : Saddam                                                                                                                                                  #
#Date         : 2016/12/28                                                                                                                                              #
#Description  : This script will parse data JSON data using python                                                                                                      #
#########################################################################################################################################################################


# Import required packages
import json
from pprint import pprint

# Read Hardcoded JSON and Parse
temp_json = """{
                "Simple_Json_Key":"Simple_Json_Value",
                "Json_Array":[1,2,3],
                "Nested_Json":{
                                "Key1":"Value1",
                                "Key2":"Value2",
                                "Key3":"Value3"
                              },
                "Nested_Json_Array":[
                                        {
                                         "Key1":"Value1",
                                         "Key2":"Value2",
                                         "Key3":"Value3"
                                        }
                                     ]
                }"""

# Parse JSON object
json_df = json.loads(temp_json)


pprint(json_df)                                                                      # Pretty print JSON object
print("===========================================================================")
print(json_df)                                                                       # Print JSON object
print("===========================================================================")
print ("Simple_JSON : "+(json_df["Simple_Json_Key"]))                                # Print element Simple_JSON
print("===========================================================================")
print ("JSON_Array : ",(json_df["Json_Array"][1]))                                   # Print element JSON_Array
print("===========================================================================")
print ("Nested_JSON : ",(json_df["Nested_Json"]["Key1"]))                            # Print element Nested_JSON
print("===========================================================================")
print ("Nested_JSON_Array : ",(json_df["Nested_Json_Array"][0]["Key2"]))             # Print element Nested_JSON_Array
print("===========================================================================")


# Read JSON from file and Parse
with open('test_data.json', encoding='utf-8') as f:                                  # Here, store the above hardcoded JSON inside file "test_data.json"
    json_file_df = json.load(f.read())


pprint(json_df)                                                                      # Pretty print JSON object
print("===========================================================================")
print(json_df)                                                                       # Print JSON object
print("===========================================================================")
print ("Simple_JSON : "+(json_df["Simple_Json_Key"]))                                # Print element Simple_JSON
print("===========================================================================")
print ("JSON_Array : ",(json_df["Json_Array"][1]))                                   # Print element JSON_Array
print("===========================================================================")
print ("Nested_JSON : ",(json_df["Nested_Json"]["Key1"]))                            # Print element Nested_JSON
print("===========================================================================")
print ("Nested_JSON_Array : ",(json_df["Nested_Json_Array"][0]["Key2"]))             # Print element Nested_JSON_Array
print("===========================================================================")

#########################################################################################################################################################################
# Output :
#########################################################################################################################################################################
{u'Json_Array': [1, 2, 3],
 u'Nested_Json': {u'Key1': u'Value1', u'Key2': u'Value2', u'Key3': u'Value3'},
 u'Nested_Json_Array': [{u'Key1': u'Value1',
                         u'Key2': u'Value2',
                         u'Key3': u'Value3'}],
 u'Simple_Json_Key': u'Simple_Json_Value'}
======================================================================================================================================================
{u'Simple_Json_Key': u'Simple_Json_Value', u'Nested_Json': {u'Key3': u'Value3', u'Key2': u'Value2', u'Key1': u'Value1'}, u'Json_Array': [1, 2, 3], u'Nested_Json_Array': [{u'Key3': u'Value3', u'Key2': u'Value2', u'Key1': u'Value1'}]}
======================================================================================================================================================
Simple_JSON : Simple_Json_Value
======================================================================================================================================================
('JSON_Array : ', 2)
======================================================================================================================================================
('Nested_JSON : ', u'Value1')
======================================================================================================================================================
('Nested_JSON_Array : ', u'Value2')
======================================================================================================================================================
#########################################################################################################################################################################
