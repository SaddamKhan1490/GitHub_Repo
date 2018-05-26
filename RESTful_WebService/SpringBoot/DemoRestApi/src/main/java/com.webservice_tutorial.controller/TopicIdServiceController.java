/**
  * Created by Saddam Khan on 5/24/2018.
  */

package com.webservice_tutorial.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.springbootstarter.model.Topic;
import com.cognizant.springbootstarter.service.TopicService;

@RestController
public class TopicIdServiceController {
	
	@Autowired
	private TopicService topicService;
	
	@RequestMapping("/topic_service/{topic_id}")							        // While making request over the browser what we can do is "localhost:8080/topic_service/topic_id" where 'topic_id' can be any existing 'id' value of topic '/topic_service' i.e. Java, Spring, Unit_Testing, etc... 
	public Topic getTopic(@PathVariable("topic_id") String id){ 			// Here instead of using another variable name for calling "topic_id" i.e. (@PathVariable("topic_id") String id), we can use "id" itself i.e. (@PathVariable String id)
		return topicService.getTopic(id);
	}

}
