/**
 * Date :-  02/09/16.
 * Author :- Vidya
 */
 
  import kafka.consumer.Consumer;
  import kafka.consumer.ConsumerConfig;
  import kafka.consumer.ConsumerIterator;
  import kafka.consumer.KafkaStream;
  import kafka.javaapi.consumer.ConsumerConnector;
  import java.util.HashMap;
  import java.util.List;
  import java.util.Map;
  import java.util.Properties;
  
  public class simpleBrokerConsumer {
  
      private final ConsumerConnector consumer;
      private final String topic;
      
      // Default initialising consumer with global scope
      public simpleBrokerConsumer(String zookeeper, String groupId, String topic) {
          Properties props = new Properties();
          props.put("zookeeper.connect", zookeeper);
          props.put("group.id", groupId);
          props.put("zookeeper.session.timeout.ms", "500");
          props.put("zookeeper.sync.time.ms", "250");
          props.put("auto.commit.interval.ms", "1000");
  
          consumer = Consumer.createJavaConsumerConnector(new ConsumerConfig(props));
          this.topic = topic;
      }
      
      // Testing the consumer & closing the connection after consuming all the messages
      public void testConsumer() {
          Map<String, Integer> topicCount = new HashMap<>();
          topicCount.put(topic, 1);
  
          Map<String, List<KafkaStream<byte[], byte[]>>> consumerStreams = consumer.createMessageStreams(topicCount);
          List<KafkaStream<byte[], byte[]>> streams = consumerStreams.get(topic);
          for (final KafkaStream stream : streams) {
              ConsumerIterator<byte[], byte[]> it = stream.iterator();
              while (it.hasNext()) {
                  System.out.println("Message from Single Topic: " + new String(it.next().message()));
              }
          }
          if (consumer != null) {
              consumer.shutdown();
          }
      }
      
      // Creating new consumer object to consume messages
      public static void main(String[] args) {
          String topic = args[0];
          simpleBrokerConsumer simpleConsumer = new simpleBrokerConsumer("localhost:2181", "testgroup", topic);
          simpleConsumer.testConsumer();
      }
  
  }
