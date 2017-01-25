/**
 * Date :-  25/01/17
 * Author :- Saddam Khan
 */
 
import java.util.*;
import java.net.URI;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.kafka.clients.producer.*;

public class PPG_Producer_SSL {
	
	 public static void main(String[] args) throws Exception{
		  
		  // Read & Load config.properties file
		  final Properties prop = new Properties();			    
		  InputStream input = new FileInputStream("config.properties");
		  prop.load(input);
		  
		  // Variable declaration
	      String topicName = prop.getProperty("topic");
		  String key = "Key";
		  String value=null;
		  

		  	  // Create kafka properties object
		  	  Properties props = new Properties();
		  	  
		  	  // Pass parameters from config.properties file object i.e.prop, to kafka properties object i.e.props
		      props.put("bootstrap.servers", prop.getProperty("brokers"));
		      props.put("acks", prop.getProperty("acks"));
		      props.put("retries", prop.getProperty("retries"));
		      props.put("batch.size", prop.getProperty("b_size"));
		      props.put("linger.ms", prop.getProperty("l_ms"));
		      props.put("buffer.memory", prop.getProperty("buff_mem"));
		      props.put("key.serializer",prop.getProperty("k_serializer"));         
		      props.put("value.serializer", prop.getProperty("v_serializer"));
	          props.put("security.protocol", prop.getProperty("protocol"));
	          props.put("ssl.truststore.location", prop.getProperty("t_loc"));
	          props.put("ssl.keystore.location", prop.getProperty("k_loc"));  	 
	          props.put("ssl.truststore.password", prop.getProperty("t_pass"));
	          props.put("ssl.keystore.password", prop.getProperty("k_pass"));
	          
	          // Create and Initialize producer
	          Producer<String, String> producer = new KafkaProducer <String, String>(props);
	          String line;
					// Set Input Path for Parser.
			    	Path srcPath = new Path(prop.getProperty("src_path"));
			    	final Configuration Conf = new Configuration();
			    	FileSystem tarsrc = srcPath.getFileSystem(Conf);
			    	final DistributedFileSystem dFS = new DistributedFileSystem() {{initialize(new URI(prop.getProperty("uri")),Conf);}};
			    	
			    	// Feed one file at a time to Parser.
			    	RemoteIterator<LocatedFileStatus> it1 = tarsrc.listFiles(srcPath, false);
			    	while (it1.hasNext()) {
						Path fileName = ((LocatedFileStatus) it1.next()).getPath();
						FSDataInputStream streamReader = dFS.open(fileName);
						FileSystem fs = FileSystem.get(new Configuration());         
			 	        
						// Start reading the input file from HDFS.
			 	        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(fs.open(fileName)));
			 	        String msg = null;
			 	        while((line = bufferedReader.readLine()) != null) {			    	        	 
			 	        	 	try {
									String suffix =line;							        
							        msg += suffix+",\\n,";							        
									}catch (Exception e) {
									 e.printStackTrace();
									 System.exit(1);
									}	
						}
		
					 	// Publish message
						msg = msg.substring(4, msg.length() - 4);
						value = msg;
						ProducerRecord<String, String> record = new ProducerRecord<String, String>(topicName,key,value);
						producer.send(record);
						
						// Close current file    
						bufferedReader.close(); 
						streamReader.close();					 
				  } 
			    	
			  // Close producer
			  dFS.close();
			  producer.close();
	  
		  System.out.println("Producer Job Completed...");
	 }
}
