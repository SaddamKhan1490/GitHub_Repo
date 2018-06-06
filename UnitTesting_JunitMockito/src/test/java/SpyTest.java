/**
  * Created by Saddam on 05/31/2018.
  */
 
package com.example.unit_testing;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.spy;


import java.util.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SpyTest {

    @Before
    public void before() {
      System.out.println("Inside before()...");
    }

    @Test
    public void test() {

      List arrayListMock = mock(ArrayList.class);

      assertEquals(0, arrayListMock.size());

      stub(arrayListMock.size()).toReturn(2);	

      arrayListMock.add("any_string");

      assertEquals(2, arrayListMock.size());

    }

    @Test
    public void testSpy() {

      List arrayListSpy = mock(ArrayList.class);


      assertEquals(0, arrayListSpy.size());

      arrayListSpy.add("any_string");

      assertEquals(1, arrayListSpy.size());

      arrayListSpy.remove("any_string");

      assertEquals(0, arrayListSpy.size());

    }

    @Test
    public void testSpyStub() {

      List arrayListMock = mock(ArrayList.class);

      stub(arrayListMock.size()).toReturn(2);	

      assertEquals(2, arrayListMock.size());

    }

    @Test
    public void testSpyVerify() {

      List arrayListSpy = mock(ArrayList.class);

      arrayListSpy.add("any_string");

      verify(arrayListSpy).add("any_string");                 // Verify the overidden object arrayListSpy		
      verify(arrayListSpy, never()).clear();                  // Verify the overidden object arrayListSpy


    }


    @After
    public void after() {
      System.out.println("Inside after()...");
    }
	
}
