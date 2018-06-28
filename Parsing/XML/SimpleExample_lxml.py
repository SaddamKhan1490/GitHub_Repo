#########################################################################################################################################################################
#Developer    : Saddam                                                                                                                                                  #
#Date         : 2016/12/28                                                                                                                                              #
#Description  : This script will parse XML data using python                                                                                                            #
#########################################################################################################################################################################


# Import required packages
from lxml import objectify
tree = objectify.fromstring(temp_xml)

# Read Hardcoded XML and Parse
temp_xml = """<xml_api_reply version="1">
					<xml_root module_id="0" tab_id="0" mobile_row="0" mobile_zipped="1" row="0" section="0" >
						<xml_first_sub_root>
						  <city data="Mountain View, CA"/>
						  <postal_code data="94043"/>
						  <latitude_e6 data=""/>
						  <longitude_e6 data=""/>
						  <forecast_date data="2010-06-23"/>
						  <current_date_time data="2010-06-24 00:02:54 +0000"/>
						  <unit_system data="US"/>
						</xml_first_sub_root>
						<xml_second_sub_root>
						  <condition data="Sunny"/>
						  <temp_f data="68"/>
						  <temp_c data="20"/>
						  <humidity data="Humidity: 61%"/>
						  <icon data="/ig/images/xml_root/sunny.gif"/>
						  <wind_condition data="Wind: NW at 19 mph"/>
						</xml_second_sub_root>
						<xml_third_sub_root>
						  <day_of_week data="Sat"/>
						  <low data="59"/>
						  <high data="75"/>
						  <icon data="/ig/images/xml_root/partly_cloudy.gif"/>
						  <condition data="Partly Cloudy"/>
						</xml_third_sub_root>
					</xml_root>
				</xml_api_reply>"""
				
print("===========================================================================")
print (tree.xml_root.attrib["module_id"])                                                # Print 'module_id' of <xml_root/> tag                                         
print("===========================================================================")
print (tree.xml_root.xml_first_sub_root.city.attrib["data"])                           # Print 'data' of <city/> tag, which is present under <xml_first_sub_root/> tag, which is further present under <xml_root/> tag                                 
print("===========================================================================")
print (tree.xml_root.xml_first_sub_root.postal_code.attrib["data"])                    # Print 'data' of <postal_code/> tag, which is present under <xml_first_sub_root/> tag, which is further present under <xml_root/> tag                                       
print("===========================================================================")


# Read XML from file and Parse
with open('test_data.xml', encoding='utf-8') as f:								        # Here, store the above hardcoded JSON inside file "test_data.json"
    tree_file = objectify.fromstring(f.read())

print("===========================================================================")
print (tree_file.xml_root.attrib["module_id"])                                                # Print 'module_id' of <xml_root/> tag                                         
print("===========================================================================")
print (tree_file.xml_root.xml_first_sub_root.city.attrib["data"])                           # Print 'data' of <city/> tag, which is present under <xml_first_sub_root/> tag, which is further present under <xml_root/> tag                                 
print("===========================================================================")
print (tree_file.xml_root.xml_first_sub_root.postal_code.attrib["data"])                    # Print 'data' of <postal_code/> tag, which is present under <xml_first_sub_root/> tag, which is further present under <xml_root/> tag                                       
print("===========================================================================")

#########################################################################################################################################################################
Output :-
#########################################################################################################################################################################
===========================================================================
0
===========================================================================
Mountain View, CA
===========================================================================
94043
===========================================================================
===========================================================================
0
===========================================================================
Mountain View, CA
===========================================================================
94043
===========================================================================
