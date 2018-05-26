/**
  * Created by Saddam Khan on 5/24/2018.
  */

package com.webservice_tutorial.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cognizant.springbootstarter.model.Topic;

@Service
public class DeleteTopicService {
	
	private List<Topic> topic = new ArrayList<>(Arrays.asList(
			new Topic("001", "Springs", "Springs Framework Descriptionn..."),
			new Topic("001", "Hibernate", "Hibernate Framework Descriptionn..."),
			new Topic("001", "Web_Services", "Web_Services Framework Descriptionn..."),
			new Topic("001", "Unit_Testing", "JUnit_Mockito Framework Descriptionn...")
			));

	public void deleteTopic(String element_id) {
		
		topic.removeIf(t -> t.getId().equals(element_id));                        // removeIf : Lambda Predicate used to remove entire List for '/topic_service' element set corresponding to specified element_id
		
	}

}
