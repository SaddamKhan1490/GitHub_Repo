/**
  * Created by Saddam Khan on 05/31/2018.
  */
 
package com.example.topic.model-dao-service-controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.topic.Topic;

@RestController
public class TopicController {
	
	
    @Autowired
    private TopicService topicService;


    @RequestMapping("/topics")																// @GetMapping can be used as alternative to @RequestMapping	
    public String getAllTopic() {
      return "All Topics";
    }


    @RequestMapping("/topics/{topic_id}")													// While making request over the browser what we can do is "localhost:8080/topic_service/topic_id" where 'topic_id' can be any existing 'id' value of topic '/topic_service' i.e. Java, Spring, Unit_Testing, etc... 
    public Optional<Topic> getTopic(@PathVariable("topic_id") String id){ 					// Here instead of using another variable name for calling "topic_id" i.e. (@PathVariable("topic_id") String id), we can use "id" itself i.e. (@PathVariable String id)
      return topicService.getTopic(id);
    }


    @RequestMapping(method=RequestMethod.POST, value="/topics")								// @PostMapping can be used as alternative to @RequestMapping 
    public void addTopic(@RequestBody Topic topic){ 										// @RequestBody                            ==> Used to take JSON i.e. request payload |ex: Topic i.e. JSON, and convert that JSON to topic instance | ex-> topic i.e. new record of list
      topicService.addTopic(topic);							
    }


    @RequestMapping(method=RequestMethod.PUT, value="/topics/{id}")							// @PutMapping can be used as alternative to @RequestMapping
    public void updateTopic(@RequestBody Topic topic,@PathVariable String id){ 				// @RequestBody                            ==> Used to take JSON i.e. request payload |ex: Topic i.e. JSON, and convert that JSON to topic instance | ex-> topic i.e. new record of list
      topicService.updateTopic(id, topic);							
    }


    @RequestMapping(method=RequestMethod.DELETE, value="/topics/{id}")						// @DeleteMapping can be used as alternative to @RequestMapping
    public void deleteTopic(@PathVariable String id){ 										
      topicService.deleteTopic(id);							
    }

}
