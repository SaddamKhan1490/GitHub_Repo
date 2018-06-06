/**
  * Created by Saddam on 05/31/2018.
  */
 
package com.example.unit_testing;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class Controller {
		
		public String helloWorld() {
			return "Hello World";
		}
		
}
