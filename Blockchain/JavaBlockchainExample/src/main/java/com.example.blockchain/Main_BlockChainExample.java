/**
  * Created by Saddam on 05/14/2018
  */

package com.example.blockchain;

import java.util.Arrays;

public class Main_BlockChainExample {

    public static void main(String[] args) {

        /*
        BlockChain -
        Block - Hash of Previous Block + Transactions
        Chained together.
         */

        // Create sequence of input record for our blockchain ledger
        Transaction transaction1 = new Transaction("Peter", "Sam", 100L);
        Transaction transaction2 = new Transaction("Sam", "Ryan", 1000L);
        Transaction transaction3 = new Transaction("Sam", "Ryan", 1000L);
        Transaction transaction4 = new Transaction("Ryan", "Peter", 150L);

        // Insert first record in the chain i.e. hashcode value will be 0 (no previous chain exist for these records)
        Block firstBlock = new Block(0, Arrays.asList(transaction1, transaction2));
        System.out.println(firstBlock.hashCode());
        
        // Insert second record in the chain i.e. hashcode value will be that of first block i.e. corresponding to previous records hash value (as records already exist for this chain)
        Block secondBlock = new Block(firstBlock.hashCode(), Arrays.asList(transaction3));
        System.out.println(secondBlock.hashCode());
        
        // Insert next record in the chain i.e. hashcode value will be that of second block i.e. corresponding to previous records hash value (as records already exist for this chain)
        Block thirdBlock = new Block(secondBlock.hashCode(), Arrays.asList(transaction4));
        System.out.println(thirdBlock.hashCode());

    }
}
