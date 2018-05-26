/**
  * Created by Saddam Khan on 5/24/2018.
  */

package com.webservice_tutorial.service;

import org.springframework.stereotype.Service;
import com.cognizant.springbootstarter.model.Topic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PostTopicService {

	private List<Topic> topic = new ArrayList<>(Arrays.asList(
			new Topic("001", "Springs", "Springs Framework Descriptionn..."),
			new Topic("001", "Hibernate", "Hibernate Framework Descriptionn..."),
			new Topic("001", "Web_Services", "Web_Services Framework Descriptionn..."),
			new Topic("001", "Unit_Testing", "JUnit_Mockito Framework Descriptionn...")
			));

	public void addTopic(Topic new_topic_element) {
		topic.add(new_topic_element);
	}
			
}
