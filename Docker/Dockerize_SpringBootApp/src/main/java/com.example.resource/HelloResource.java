/**
  * Created by Saddam on 04/28/2018
  */

package com.example.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController                                   // @RestController annotation is used to associate REST controller definition with HelloResource class 
@RequestMapping("/rest/docker/hello")             // @RequestMapping annotation is used to specify REST request URL for our application
public class HelloResource {

	@GetMapping                                     // @GetMapping annotation is used to generate HTTP GET response for given request
	public String hello() {
		return "Hello World";
	}
}
