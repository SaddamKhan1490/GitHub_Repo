/**
  * Created by Saddam Khan on 5/24/2018.
  */

package com.webservice_tutorial.service;

import com.cognizant.springbootstarter.model.Topic;
import com.cognizant.springbootstarter.service.TopicService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TopicServiceController {

	@Autowired
	private TopicService topicService;
	
	@RequestMapping("/topic_service")
	public List<Topic> getAllTopic(){
		return topicService.getAllTopic();
	}
		
}
