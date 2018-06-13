/**
  * Created by Saddam on 04/14/2018
  */
  

package com.example.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.example.SpringMongo.document.Users;
import com.example.SpringMongo.respository.UserRepository;

@EnableMongoRepositories(basePackageClasses = UserRepository.class)
@Configuration
public class MongoDBConfig {
	
	 @Bean
	    CommandLineRunner commandLineRunner(UserRepository userRepository) {
	        return strings -> {
	            userRepository.save(new Users(1, "Peter", "Development", 3000L));
	            userRepository.save(new Users(2, "Sam", "Operations", 2000L));
	        };
	    }

}
