/**
  * Created by Saddam on 04/28/2016
  */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/* Input Message Format i.e. here message comprises of Simple JSON, Nested JSON, Simple JSON Array:-
{"emp_id":100000101,"emp_name":"aaaaaaa","dob":"2000-12-31","doj":"2020-01-01","dept":"technology","location_ids":[1001, 2001, 3001],"location_continent":{"asia":"Y","america":"N","europe":"N"},"address":"H No 10, xyz street","salary":1000000} 
*/

public class Parser {

		public static void main(String[] args) {
			
				// Specify input & output file paths
		    	String source_filepath = "C:\\Users\\Lenovo\\Desktop\\Test_data.json";
		    	String destination_filepath = "C:\\Users\\Lenovo\\Desktop\\Parsed_Test_data.json";
		
			    // This will reference one line at a time
			    String line = null;    
			    
			    try {
			    	 // FileReader reads text files in the default encoding.
			         FileReader fileReader = new FileReader(source_filepath);
		
			         // Always wrap FileReader in BufferedReader.
			         BufferedReader bufferedReader = new BufferedReader(fileReader);
			         
			         // FileWriter reads text files in the default encoding.
			         FileWriter fileWriter = new FileWriter(destination_filepath);
		
			         // Always wrap FileReader in BufferedWriter.
			         BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			         
			         System.out.println("Begining writing to file...");
			         
			         // Read and Parse input file record by record  
			         while((line = bufferedReader.readLine()) != null) {
							try {
										// Create objectMapper object
			    						ObjectMapper mapper = new ObjectMapper();
			    						
			    						// Create tree view of input JSON object
			    						JsonNode root = mapper.readTree(line);
			    						
			    						// Declare global variables in order to capture message
			    						long emp_id = root.path("emp_id").asLong();
			    						String emp_name = root.path("emp_name").asText();
			    						String dob = root.path("dob").asText();
			    						String doj = root.path("doj").asText();
			    						String dept = root.path("dept").asText();
			    						long salary = root.path("salary").asLong();	
			    						String location_ids = root.path("location_ids").toString();		    						
			    						String asia = null;
			    						String america = null;
			    						String europe = null;		    						
			    						String address = root.path("address").asText();
			    						
			    						// Capture current timestamp
			    						TimeZone tz = TimeZone.getTimeZone("CST");
			    						DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			    						df1.setTimeZone(tz);
			    						String nowAsISO = df1.format(new Date());
								
										// Capture JSON array object to list 		
			    						String [] items = location_ids.substring(1,(location_ids.length()-1)).split(",");
			    						List<String> container = Arrays.asList(items);
			    						
			    						// Parse nested JSON object
			    						JsonNode nameNode = root.path("location_continent");
			    						if (nameNode.isMissingNode()) {
			    							// if "name" node is missing
			    						} else {
			    							asia = nameNode.path("asia").asText();
			    							america = nameNode.path("america").asText();
			    							europe = nameNode.path("europe").asText();
			    						}
																	    									
												for (int i=0; i<container.size() ; i++){
													String current_loc = container.get(i);
											        String msg = emp_id + "|" + emp_name + "|" + dob + "|" + doj + "|" + dept + "|" + salary + "|" + current_loc + "|" + asia + "|" + america + "|" + europe + "|" + address + "|" + nowAsISO ;
											        System.out.println(msg);
											        bufferedWriter.write(msg);bufferedWriter.write("\n");
											    }
					
									
			    					} catch (JsonGenerationException e) {
			    						e.printStackTrace();
			    					} catch (JsonMappingException e) {
			    						e.printStackTrace();
			    					} catch (IOException e) {
			    						e.printStackTrace();
			    					}
							  				   
				         }
			         
				         bufferedReader.close();
				         bufferedWriter.close();
						 
						 System.out.println("succesfully written to a file");
						 
			    }catch(FileNotFoundException ex) {    
			    System.out.println("Unable to open file '" + source_filepath + "'");                        
			    } catch (JsonGenerationException e) {
			    e.printStackTrace();
			 	} catch (JsonMappingException e) {
			 	e.printStackTrace();
			 	} catch (IOException e) {
			 	e.printStackTrace();
			 	}
			    
		}
	

}

/* Sample Output:-
100000101|aaaaaaa|2000-12-31|2020-01-01|technology|1000000|1001|Y|N|N|H No 10, xyz street|2016-04-28 03:03:41
100000101|aaaaaaa|2000-12-31|2020-01-01|technology|1000000|2001|Y|N|N|H No 10, xyz street|2016-04-28 03:03:41
100000101|aaaaaaa|2000-12-31|2020-01-01|technology|1000000|3001|Y|N|N|H No 10, xyz street|2016-04-28 03:03:41
*/
