/**
  * Created by Saddam on 05/31/2018.
  */
 
package com.example.unit_testing;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class ControllerJson {
	
	@GetMapping(value="/json/get", produces=MediaType.APPLICATION_JSON_VALUE)
	public Hello helloWorld() {
		return new Hello("Key", "Hello World");
	}
	
	@PostMapping(value="/json/post", consumes=MediaType.APPLICATION_JSON_VALUE)
	public Hello helloWorldPost(@RequestBody Hello hello) {
		return hello;
	}
	
	private class Hello{
		
		private String key;
		private String value;

		
		public Hello(String key, String value) {
			super();
			this.key = key;
			this.value = value;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}

	}
}
