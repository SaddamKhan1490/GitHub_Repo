/**
  * Created by Saddam on 05/31/2018.
  */
 
package com.example.unit_testing;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

public class MathTestWithMockitoVerify {

    Maths math_obj;

    @Before
    public void before() {

      System.out.println("Inside Before()...");
      math_obj = mock(Maths.class);						// Create mock object of math class i.e. not actual object 
      when(math_obj.add(1, 2)).thenReturn(3);				// Configure return value of the method with dummy value when add(1,2) is called

    }

    @Test
    public void test() {

      System.out.println("Inside Test() whitout Mockito...");
      assertSame(3, math_obj.add(1, 2));					// While testing we are not refering actual object rather using passed object
      verify(math_obj).add(eq(1),eq(2));					// Verify add() method is tested with argument (1,2) or not
      verify(math_obj, times(1)).add(1,2);				// Verify wether add() method is called only once or not

    }
}
