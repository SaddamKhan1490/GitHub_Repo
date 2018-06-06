/**
  * Created by Saddam on 05/31/2018.
  */
 
package com.example.unit_testing;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

public class MathTestWithMockitoThrowingException {

    Maths math_obj;

    @Before
    public void before() {

      System.out.println("Inside Before()...");
      math_obj = mock(Maths.class);						// Create mock object of math class i.e. not actual object 
      when(math_obj.div(anyInt(), eq(0))).thenThrow(new ArithmeticException());				// Define behaviour to throw exception

    }

    @Test(expected=ArithmeticException.class)
    public void test() {

      System.out.println("Inside Test() whitout Mockito...");
       math_obj.div(1, 0);								// Call method expecting Arithmetic Exception i.e. if excetion occours in this step then aour JUnit test will pass and will go green

    }
