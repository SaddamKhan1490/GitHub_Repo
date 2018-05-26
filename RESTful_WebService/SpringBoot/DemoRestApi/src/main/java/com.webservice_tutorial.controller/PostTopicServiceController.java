/**
  * Created by Saddam Khan on 5/24/2018.
  */

package com.webservice_tutorial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.springbootstarter.model.Topic;
import com.cognizant.springbootstarter.service.PostTopicService;

@RestController
public class PostTopicServiceController {
	
	@Autowired
	private PostTopicService postTopicService;
	
	@RequestMapping(method=RequestMethod.POST, value="/topic_service")							// method=RequestMethod.POST, value='/url' ==> Used to associate method to that of POST type for specified URL
	public void addTopic(@RequestBody Topic topic){ 											// @RequestBody                            ==> Used to take JSON i.e. request payload |ex: Topic i.e. JSON, and will convert that JSON to plain text topic instance | ex-> topic i.e. new record of list
	postTopicService.addTopic(topic);							
		
	}

}
