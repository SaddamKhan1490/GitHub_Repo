/**
  * Created by Saddam on 05/31/2018.
  */
 
package com.example.unit_testing;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.*;

public class ListTest {

    @Before
    public void before() {
      System.out.println("Inside before()...");
    }

    @Test
    public void testMockListSize() {

      System.out.println("Inside testMockListSize()...");

      List listMock = mock(List.class);
      when(listMock.size()).thenReturn(2);
      assertEquals(2,listMock.size());

    }

    @Test
    public void testMockListSize_ReturnMultipleValue() {

      System.out.println("Inside testMockListSize()...");

      List listMock = mock(List.class);
      when(listMock.size()).thenReturn(2).thenReturn(3);
      assertEquals(2,listMock.size());
      assertEquals(3,listMock.size());

    }

    @Test
    public void testMockListElement() {

      System.out.println("Inside testMockListElement()...");

      List listMock = mock(List.class);
      when(listMock.get(0)).thenReturn("two");                                      // Create stub to deal with get element of list
      assertEquals("two",listMock.get(0));

    }

    @Test
    public void testMockListNiceMock() {

      System.out.println("Inside testMockListNiceMock()...");

      List listMock = mock(List.class);

      assertEquals(null,listMock.get(0));                                            // Calling non-stubbed method, which leads to calling non-stubbed method
      assertEquals(null,listMock.get(1));                                            // Calling non-stubbed method, which leads to calling non-stubbed method

    }

    @Test(expected=RuntimeException.class)
    public void testMockListException() {

      System.out.println("Inside testMockListException()...");

      List listMock = mock(List.class);
      when(listMock.get(anyInt())).thenThrow(new RuntimeException("Something"));      // Throw exception in case if we are calling get method on list with any index i.e. numeric in nature 

      listMock.get(0);

    }

    @After
    public void after() {
      System.out.println("Inside after()...");
    }

}
