/**
  * Created by Saddam Khan on 05/31/2018.
  */
  
package com.example.course.model-dao-service-controller;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.topic.model-dao-service-controller.Topic;

public interface CourseRepository extends CrudRepository <Course, String>{

	public List<Course> findByName(String name);						  // Spring data JPA will try to split the given string into two parts i.e. findBy & Name, and will try to match Name with any of the existing variable which may be present inside "Course" class and will for successful match will return the associated spring object i.e. Course class object as its the return type of define function i.e. findByName

  	public List<Course> findByDescription(String description);            			  // Spring data JPA will try to split the given string into two parts i.e. findBy & Description, and will try to match Description with any of the existing variable which may be present inside "Course" class and will for successful match will return the associated spring object i.e. Course class object as its the return type of define function i.e. findByDescripton    
	
	public List<Topic> findByTopicId(String topic_id);					  // Spring data JPA will try to split the given string into parts i.e. findBy & Topic & Id, and will try to match Id with any of the existing variable which may be present inside "Topic" class and will for successful match will return the associated spring object i.e. Topic class object as its the return type of define function i.e. findByTopicId  

}
