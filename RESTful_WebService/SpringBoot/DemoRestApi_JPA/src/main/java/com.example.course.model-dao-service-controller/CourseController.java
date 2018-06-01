/**
  * Created by Saddam Khan on 05/31/2018.
  */
  
package com.example.course.model-dao-service-controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.topic.Topic;


@RestController
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	
	
	@RequestMapping("/topics/{topic_id}/courses")																// @GetMapping can be used as alternative to @RequestMapping	
	public List<Course> getAllCourse(@PathVariable("topic_id") String id) {
		return courseService.getAllCourse(id);
	}
	
	
	@RequestMapping("/topics/{topic_id}/Courses/{Course_id}")													// While making request over the browser what we can do is "localhost:8080/Course_service/Course_id" where 'Course_id' can be any existing 'id' value of Course '/Course_service' i.e. Java, Spring, Unit_Testing, etc... 
	public Optional<Course> getCourse(@PathVariable("Course_id") String id){ 									// Here instead of using another variable name for calling "Course_id" i.e. (@PathVariable("Course_id") String id), we can use "id" itself i.e. (@PathVariable String id)
		return courseService.getCourse(id);
	}

	
	@RequestMapping(method=RequestMethod.POST, value="/topics/{topic_id}/Courses")								// @PostMapping can be used as alternative to @RequestMapping 
	public void addCourse(@RequestBody Course course, @PathVariable("topic_id") String topic_id){ 				// @RequestBody                            ==> Used to take JSON i.e. request payload |ex: Course i.e. JSON, and convert that JSON to Course instance | ex-> Course i.e. new record of list
		course.setTopic(new Topic(topic_id,"",""));
		courseService.addCourse(course);							
	}
	
	
	@RequestMapping(method=RequestMethod.PUT, value="/topics/{topic_id}/Courses/{id}")														// @PutMapping can be used as alternative to @RequestMapping
	public void updateCourse(@RequestBody Course course, @PathVariable("topic_id") String topic_id,@PathVariable String id){ 				// @RequestBody                            ==> Used to take JSON i.e. request payload |ex: Course i.e. JSON, and convert that JSON to Course instance | ex-> Course i.e. new record of list
		course.setTopic(new Topic(topic_id,"",""));
		courseService.updateCourse(course);							
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/topics/{topic_id}/Courses/{id}")						// @DeleteMapping can be used as alternative to @RequestMapping
	public void deleteTopic(@PathVariable String id){ 										
		courseService.deleteCourse(id);							
	}

}
