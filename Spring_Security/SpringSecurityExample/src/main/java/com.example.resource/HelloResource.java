/**
  * Created by Saddam on 05/14/2018
  */
  
package com.example.resource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/rest/hello")
public class HelloResource {

    @GetMapping
    public String hello(@AuthenticationPrincipal final UserDetails userDetails) {                   // @AuthenticationPrinciple is used to get complete details of requested URL i.e. which user-role-etc... have access to give URL

        String username = userDetails.getUsername();
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        authorities
                .forEach(System.out::println);                                                      // Printing complete details of all the existing user

        return "Hello World";
    }
}
