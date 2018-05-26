/**
  * Created by Saddam Khan on 5/24/2018.
  */

package com.webservice_tutorial.service;

import org.springframework.stereotype.Service;
import com.cognizant.springbootstarter.model.Topic;

import java.util.Arrays;
import java.util.List;

@Service
public class TopicService {

	private List<Topic> topic = Arrays.asList(
			new Topic("001", "Springs", "Springs Framework Descriptionn..."),
			new Topic("001", "Hibernate", "Hibernate Framework Descriptionn..."),
			new Topic("001", "Web_Services", "Web_Services Framework Descriptionn..."),
			new Topic("001", "Unit_Testing", "JUnit_Mockito Framework Descriptionn...")
			);
	
	public List<Topic> getAllTopic(){
		return topic;
	}
	
	public Topic getTopic(String id) {
		return topic.stream().filter(t -> t.getId().equals(id)).findFirst().get();
	}
			
}
