/**
  * Created by Saddam on 05/07/2018
  */
 
package com.example.resource;

import com.example.model.User;
import com.example.model.UserResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {


    @MessageMapping("/user")
    @SendTo("/topic/user")
    public UserResponse getUser(User user) {

        return new UserResponse("Hi welcome to WebSocket " + user.getName());
    }
}
