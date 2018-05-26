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
public class UpdateTopicService {

	private List<Topic> topic = new ArrayList<>(Arrays.asList(
			new Topic("001", "Springs", "Springs Framework Descriptionn..."),
			new Topic("001", "Hibernate", "Hibernate Framework Descriptionn..."),
			new Topic("001", "Web_Services", "Web_Services Framework Descriptionn..."),
			new Topic("001", "Unit_Testing", "JUnit_Mockito Framework Descriptionn...")
			));

	public void updateTopic(Topic new_topic_value, String element_id) {
		for (int i = 0; i < topic.size(); i++) {
			
			Topic t = topic.get(i);
			
			if(t.getId().equals(element_id)) {
				topic.set(i, new_topic_value);
			}
		}
	}
	
}
