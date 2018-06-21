/**
  * Created by Saddam on 04/28/2018
  */
  
  
package com.example.repositories;

import guru.springframework.domain.Product;
import org.springframework.data.neo4j.repository.Neo4jRepository;


public interface ProductRepository extends Neo4jRepository<Product, Long> {

}
