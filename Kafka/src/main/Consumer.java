/**
 * Date :-  02/09/16.
 * Author :- Saddam
 */
 
  import kafka.consumer.ConsumerIterator;
  import kafka.consumer.KafkaStream;
  
  final class consumerThread implements Runnable {
  
      private KafkaStream stream;
      private int threadNumber;
      
      // Default initialising consumer with global scope
      public consumerThread(KafkaStream stream, int threadNumber) {
          this.threadNumber = threadNumber;
          this.stream = stream;
      }
      
      // Consuming messages
      public void run() {
          ConsumerIterator<byte[], byte[]> it = stream.iterator();
          while (it.hasNext()) {
              System.out.println("Message from thread " + threadNumber + ": " + new String(it.next().message()));
          }
          System.out.println("Shutting down thread: " + threadNumber);
      }
  
  }
