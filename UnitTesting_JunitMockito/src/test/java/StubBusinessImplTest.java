/**
  * Created by Saddam on 05/31/2018.
  */
 
package com.example.unit_testing;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StubBusinessImplTest {

    @Before
    public void before() {

      System.out.println("Inside Before()...");

    }

    @Test
    public void testRetrieveRelatedToSpring_UsingStub() {

      System.out.println("Inside testRetrieveRelatedToSpring_UsingStub()...");

      StubClass stubObj = new StubClass();                                              // Create stub class object
      BusinessImpl businessObj = new BusinessImpl(stubObj);                             // Create business class object

      List<String> filterSpring = businessObj.retrieveRelatedToSpring("any_string");    // Here we can pass any string as we have alraedy hadr coded value "Spring" while making comparison inside function within BusineesImpl class 

      assertEquals(2,filterSpring.size());                                              // Perform unit test using STUB

    }

    @After
    public void after() {

      System.out.println("Inside After()...");

    }

}
