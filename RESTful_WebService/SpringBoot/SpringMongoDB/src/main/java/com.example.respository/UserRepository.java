/**
  * Created by Saddam on 04/14/2018
  */
  
  
package com.example.respository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.SpringMongo.document.Users;

public interface UserRepository extends MongoRepository<Users, Integer>{

}
