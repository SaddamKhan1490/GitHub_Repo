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
import com.cognizant.springbootstarter.service.DeleteTopicService;

@RestController
public class DeleteTopicServiceController {
	
	@Autowired
	private DeleteTopicService deleteTopicService;
	
	@RequestMapping(method=RequestMethod.DELETE, value="/topic_service/{id}")							// method=RequestMethod.DELETE, value='/url/id' ==> Used to associate method to that of DELETET type for specified URL
	public void deleteTopic(@PathVariable String id){ 													// @PathVariable 								==> Used to paas the element_id of existing group which we want to delete
		deleteTopicService.deleteTopic(id);							
	}

}
