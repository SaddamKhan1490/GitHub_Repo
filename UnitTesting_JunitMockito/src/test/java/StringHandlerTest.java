/**
  * Created by Saddam on 05/31/2018.
  */
 
package com.example.unit_testing;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.TestCase;

public class StringHandlerTest extends TestCase {
	
    StringHelper helper_object;

    @BeforeClass
    public static void beforeClass() {
      System.out.println("Before Class");
    }

    @AfterClass
    public static void afterClass() {
      System.out.println("After Class");
    }

    @Before
    public void before() {
      System.out.println("Before");
      helper_object = new StringHelper();	
    }

    @After
    public void after() {
      System.out.println("After");
      helper_object=null;
    }

    @Test
    public void testAssertEquals(){

      System.out.println("Inside testAssertEquals()...");

      assertEquals("RTAA","RTAA");
      assertEquals("RT", "RT");
      assertEquals("RAT", "RAT");

    }

    @Test
    public void testTruncateAInFirst2Positions(){

      System.out.println("Inside testTruncateAInFirst2Positions()...");

      StringHelper helper = new StringHelper();
      String expected = "RTAA";
      String actual = helper.TruncateAInFirst2Positions("RTAA");

      assertEquals(expected, actual);

    }

    @Test
    public void testAlternativeTruncateAInFirst2Positions(){

      System.out.println("Inside testAlternativeTruncateAInFirst2Positions()...");

      StringHelper helper = new StringHelper();

      assertEquals("RTAA", helper.TruncateAInFirst2Positions("RTAA"));
      assertEquals("RT", helper.TruncateAInFirst2Positions("AART"));
      assertEquals("RT", helper.TruncateAInFirst2Positions("RAT"));

    }


    @Test
    public void FirstAndLastTwoCharactersAreSame(){

      System.out.println("Inside FirstAndLastTwoCharactersAreSame()...");

      StringHelper helper = new StringHelper();

      assertFalse(helper.FirstAndLastTwoCharactersAreSame(""));
      assertFalse(helper.FirstAndLastTwoCharactersAreSame("A"));
      assertTrue( helper.FirstAndLastTwoCharactersAreSame("AA"));
      assertTrue(helper.FirstAndLastTwoCharactersAreSame("AAA"));
      assertFalse(helper.FirstAndLastTwoCharactersAreSame("AAB"));
      assertFalse(helper.FirstAndLastTwoCharactersAreSame("ABB"));	

    }

    @Test
    public void testSwapLast2Characters(){

      System.out.println("Inside testSwapLast2Characters()...");

      StringHelper helper = new StringHelper();

      assertEquals("RTAA", helper.swapLast2Characters("RTAA"));
      assertEquals("AATR", helper.swapLast2Characters("AART"));
      assertEquals("RTA", helper.swapLast2Characters("RAT"));

    }

    @Test
    public void testArraySort() {

      System.out.println("Inside testArraySort()...");

      int[] numbers = {1,6,2,3,7,4,5};
      Arrays.sort(numbers);
      int[] expected_numbers = {1,2,3,4,5,6,7};
      assertArrayEquals(expected_numbers,numbers);

    }


    @Test(expected=NullPointerException.class)
    public void testArraysSortWithNullCondition() {

      System.out.println("Inside testArraysSortWithNullCondition()...");

      int[] numbers = {};
      Arrays.sort(numbers);

    }


    @Test(timeout=10)
    public void testArraySortPerformance() {

      System.out.println("Inside testArraySortPerformance()...");

      for (int i = 0; i < 1000000000; i++) {
        int[] numbers = {i, i-1, i+1};
        Arrays.sort(numbers);
      }

    }
	
}
