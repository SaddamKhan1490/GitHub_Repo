/**
  * Created by Saddam on 05/31/2018.
  */
 
package com.example.unit_testing;

import static org.junit.Assert.*;

import org.junit.*;

public class MathsTest {

    Maths math_obj;

    @Before
    public void before() {

      System.out.println("Inside Before()...");
      math_obj = new Maths();

    }

    @Test
    public void test() {

      System.out.println("Inside Test() whitout Mockito...");
      assertSame(3, math_obj.add(1, 2));

    }

}
