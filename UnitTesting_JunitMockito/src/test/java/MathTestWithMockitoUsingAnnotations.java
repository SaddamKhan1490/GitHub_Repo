/**
  * Created by Saddam on 05/31/2018.
  */
 
package com.example.unit_testing;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.junit.*;
import org.mockito.*;

import org.mockito.Mock;

public class MathTestWithMockitoUsingAnnotations {

    @Mock                                           // Create mock object using annotations 
    Maths math_obj;

    @Before
    public void before() {

      System.out.println("Inside Before()...");
      MockitoAnnotations.initMocks(this);           // initMock() is static method to create mocked object
      when(math_obj.add(1, 2)).thenReturn(3);       // Configure return value of the method
    }


    @Test
    public void test() {

      System.out.println("Inside Test() whitout Mockito...");
      assertSame(3, math_obj.add(1, 2));            // While testing we are not refering actual object rather using passed object
    }

}
