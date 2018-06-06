/**
  * Created by Saddam on 05/31/2018.
  */
 
package com.example.unit_testing;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MockBusinessImplTest {

	@Before
	public void before() {
		
		System.out.println("Inside Before()...");
		
	}
	
	@Test
	public void testRetrieveRelatedToSpring_UsingMock() {
		
		System.out.println("Inside testRetrieveRelatedToSpring_UsingMock()...");

		StubClass mockObj = mock(StubClass.class); 							// Create stub class object
		
		List<String> input = Arrays.asList("Spring MVC", "Learn Spring");				// Here we can directly create the mock object with different number of parameter, and without being dependent on any other interface or class
		
		when(mockObj.retrieve("any_string")).thenReturn(input);
			
		BusinessImpl businessObj = new BusinessImpl(mockObj);						// Create business class object
		
		List<String> filterSpring = businessObj.retrieveRelatedToSpring("any_string");   		// Here we can pass any string as we have alraedy hadr coded value "Spring" while making comparison inside function within BusineesImpl class 
		
		assertEquals(2,filterSpring.size());								// Perform unit test using STUB
		
	}
	
	@After
	public void after() {
		
		System.out.println("Inside After()...");
		
	}


}
