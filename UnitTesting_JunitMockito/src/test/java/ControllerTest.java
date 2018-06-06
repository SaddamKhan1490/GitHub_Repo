/**
  * Created by Saddam on 05/31/2018.
  */
 
package com.example.unit_testing;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;

@RunWith(SpringJUnit4ClassRunner.class)
public class ControllerTest {

    private Mock mockMvc;

    @ InjectMocks 
    private Controller controllerObj;                  							 // Create controller class mock object instance

    @BeforeClass
    public static void beforeClass() {
      System.out.println("Before Class");

      mockMvc = MockMvcBuilder.StandAloneSetUp(controllerObj).build();         				// Create stand alone set up for REST end point

    }

    @AfterClass
    public static void afterClass() {
      System.out.println("After Class");
    }

    @Test
    public void testControllerWithTextDataAtRequetEndPoint() throws Exception {

      mockMvc.perform(
              MockMvcRequestBuilder.get("/hello")							// Hit the request i.e. GET, end point i.e. /hello
              ) 					
              .andExpect(MockMvcResultMatchers.status().isOk())
              .andExpect(MockMvcResultMatchers.content().string("Hello World"));

    }

    @Test
    public void testControllerWithTextDataAtRequetEndPoint_NeatCode() throws Exception {

      mockMvc.perform(
              get("/hello")										// Hit the request i.e. GET, end point i.e. /hello
              ) 					
              .andExpect(status().isOk())
              .andExpect(content().string("Hello World"));

    }

    @Test
    public void testControllerWithJsonDataAtRequetEndPoint_Get_NeatCode() throws Exception {

      mockMvc.perform(
              get("/hello/json/get").accept(MediaType.APPLICATION_JSON)					// Hit the request i.e. GET, end point i.e. /hello
              ) 					
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.key", Matchers.is("Key")))
              .andExpect(jsonPath("$.value", Matchers.is("Hello World")));

    }

    @Test
    public void testControllerWithJsonDataAtRequetEndPoint_Post_NeatCode() throws Exception {

      mockMvc.perform(
              post("/hello/json/post")
              .contentType(MediaType.APPLICATION_JSON)							// Hit the request i.e. POST, end point i.e. /hello
              .content(json)
              ) 					
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.key", Matchers.is("Key")))
              .andExpect(jsonPath("$.value", Matchers.is("Hello World")))
              .andExpect(jsonPath("$.*", Matchers.hasSize(2)));
    }
	
}
