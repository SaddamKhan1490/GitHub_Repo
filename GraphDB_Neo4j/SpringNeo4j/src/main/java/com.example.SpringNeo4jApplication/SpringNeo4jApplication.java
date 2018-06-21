/**
  * Created by Saddam on 04/28/2018
  */
  
  
package com.example.SpringNeo4jApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
public class SpringNeo4jApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringNeo4jApplication.class, args);
	}
}
