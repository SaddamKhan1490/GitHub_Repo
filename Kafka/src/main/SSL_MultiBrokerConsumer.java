
/**
 * Date :-  25/01/17
 * Author :- Saddam Khan
 */
 
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;

public class SSL_Consumer {

	 public static void main( String[] args ) throws IOException
	    {
          // Read & Load config.properties file
    		  final Properties prop = new Properties();			    
    		  InputStream input = new FileInputStream("config.properties");
    		  prop.load(input);

          // Create kafka properties object
  	      Properties props = new Properties();
          
          // Pass parameters from config.properties file object i.e.prop, to kafka properties object i.e.props          
  	      props.put("bootstrap.servers", prop.getProperty("brokers"));
  	      props.put("group.id", prop.getProperty("groupId"));
  	      props.put("key.deserializer", prop.getProperty("k_serializer"));
  	      props.put("value.deserializer", prop.getProperty("v_serializer"));
  	      props.put("buffer.memory", prop.getProperty("buff_mem"));
  	      props.put("enable.auto.commit", prop.getProperty("acks"));
  	      props.put("auto.commit.interval.ms", prop.getProperty("frequency"));
  	      props.put("auto.offset.reset", prop.getProperty("off_type"));
          props.put("security.protocol", prop.getProperty("protocol"));
          props.put("ssl.truststore.location", prop.getProperty("t_loc"));
          props.put("ssl.keystore.location", prop.getProperty("k_loc"));  	 
          props.put("ssl.truststore.password", prop.getProperty("t_pass"));
          props.put("ssl.keystore.password", prop.getProperty("k_pass"));	 

  	      // Create and Initialize consumer
  	      KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
                        // Receive message
  	                    consumer.subscribe(Arrays.asList(prop.getProperty("topic")));
  	                    consumer.commitSync();
  	                    consumer.pause();
  	                    try {
  	                             BufferedWriter output = new BufferedWriter(new FileWriter(new File(prop.getProperty("src_path"))));
  	                             while(true){
  	                                ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
  	                                for (ConsumerRecord<String, String> record : records){
  	                                System.out.println(record.offset() + " | " + record.partition() + " | " + record.value());
      	                            }
      	                        }
  	                    } catch (WakeupException e) {
  	                             e.printStackTrace();
  	                    } finally {
                           // Close current file 
                           output.close()
                           // Close consumer
  	                       consumer.close();
  	                    }
  	    }

}
