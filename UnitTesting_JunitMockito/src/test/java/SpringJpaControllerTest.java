/**
  * Created by Saddam on 05/31/2018.
  */
 
package com.example.unit_testing;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

@RunWith(SpringRunner.class)
@WebMvcTest                                                             // Used to perform Unit Testing on Spring MVC Application
public class SpringJpaControllerTest {

	@Autowired
	private Mock mockMvc;
	
	@Mock
	private UserRepository userRepository;                                // Create controller class mock object instance
	
	@Test
	public void testContextLoad() throws Exception {

		Mockito.when(userRepository.findAll()).thenReturn(Collections.emptyList());
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilder.get("/all").accept(MediaType=APPLICATION_JSON)).andReturn();
		Mockito.verify(userRepository).findAll();
	}

}
