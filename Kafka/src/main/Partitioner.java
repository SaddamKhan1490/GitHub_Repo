/**
 * Date :-  02/09/16.
 * Author :- Vidya
 */

  import kafka.producer.Partitioner;
  import kafka.utils.VerifiableProperties;
  
  @SuppressWarnings("UnusedDeclaration")
  public class partitioner implements Partitioner {
      
      // Default itnitializing & verifying
      public partitioner(VerifiableProperties properties) {
      }
      
      // Creating partitions globally
      public int partition(Object key, int numberOfPartitions) {
          int partition = 0;
          int intKey = (Integer) key;
          if (intKey > 0) {
              partition = intKey % numberOfPartitions;
          }
          return partition;
      }
  }
