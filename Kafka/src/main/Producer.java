/**
 * Date :-  02/09/16.
 * Author :- Vidya
 */


import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

public class producer {
    private static Producer<Integer, String> producer;
    private final Properties properties = new Properties();
    
    // Initialing producer globally
    public producer() {
        properties.put("metadata.broker.list", "localhost:9092");
        properties.put("serializer.class", "kafka.serializer.StringEncoder");
        properties.put("request.required.acks", "1");
        producer = new Producer<>(new ProducerConfig(properties));
    }
    
    // Creating producer object to produce messages
    public static void main(String[] args) {
        new SimpleProducer();
        String topic = args[0];
        String msg = args[1];
        KeyedMessage<Integer, String> data = new KeyedMessage<>(topic, msg);
        producer.send(data);
        producer.close();
    }
}
