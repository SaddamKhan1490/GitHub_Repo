/**
  * Created by Saddam on 04/14/2018
  */
  
  
package com.example.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SpringMongo.document.Users;
import com.example.SpringMongo.respository.UserRepository;

import java.util.List;

@RestController
@RequestMapping("/rest/users")
public class UsersResource {

    private UserRepository userRepository;										// From Spring 4 onwards we dont need to add @Atutowired annotation to create new object

    public UsersResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public List<Users> getAll() {
        return userRepository.findAll();
    }
}
