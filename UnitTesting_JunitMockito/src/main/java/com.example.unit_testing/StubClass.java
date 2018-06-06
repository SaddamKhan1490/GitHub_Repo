/**
  * Created by Saddam on 05/31/2018.
  */
 
package com.example.unit_testing;

import java.util.*;

public class StubClass implements StubInterface{
	
	public List<String> retrieve(String input){
			
			return Arrays.asList("Spring MVC", "Learn Spring");
			
	}

}
