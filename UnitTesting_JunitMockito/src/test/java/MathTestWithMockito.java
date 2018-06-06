/**
  * Created by Saddam on 05/31/2018.
  */
 
package com.example.unit_testing;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.*;

public class MathTestWithMockito {

    Maths math_obj;

    @Before
    public void before() {

      System.out.println("Inside Before()...");
      math_obj = mock(Maths.class);						// Create mock object of math class i.e. not actual object 
      when(math_obj.add(1, 2)).thenReturn(3);				// Configure return value of the method with dummy value when add(1,2) is called
      when(math_obj.mul(anyInt(), eq(0))).thenReturn(0);  // Configure return value of the method with dummy value when mul(anyInt(), eq(0)) is called

    }

    @Test
    public void test() {

      System.out.println("Inside Test() whitout Mockito...");
      assertSame(3, math_obj.add(1, 2));					// While testing we are not refering actual object rather using passed object
      assertSame(0, math_obj.mul(1, 0));					// While testing we are not refering actual object rather using passed object

    }

}
