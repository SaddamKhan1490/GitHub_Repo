/**
  * Created by Saddam Khan on 5/24/2018.
  */

package com.webservice_tutorial.service;

import com.cognizant.springbootstarter.model.Topic;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ListTopicController {
	
	@RequestMapping("/all_topics")
	public List<Topic> getAllTopic(){
		
		return Arrays.asList(
				new Topic("001", "Springs", "Springs Framework Descriptionn..."),
				new Topic("001", "Hibernate", "Hibernate Framework Descriptionn..."),
				new Topic("001", "Web_Services", "Web_Services Framework Descriptionn..."),
				new Topic("001", "Unit_Testing", "JUnit_Mockito Framework Descriptionn...")
				);
	}

}
