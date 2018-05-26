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
import com.cognizant.springbootstarter.service.UpdateTopicService;

@RestController
public class UpdateTopicServiceController {
	
	@Autowired
	private UpdateTopicService updateTopicService;
	
	@RequestMapping(method=RequestMethod.PUT, value="/topic_service/{id}")							// method=RequestMethod.PUT, value='/url/{element_id}' ==> Used to associate method to that of PUT type for specified URL
	public void updateTopic(@RequestBody Topic topic,@PathVariable String id){ 						// @RequestBody                            			   ==> Used to take JSON i.e. request payload |ex: Topic i.e. JSON, and convert that JSON to topic instance | ex-> topic i.e. new record of list
	updateTopicService.updateTopic(topic, id);														// @PathVariable 									   ==> Used to paas the element_id of existing group which we want to update

	}
}
