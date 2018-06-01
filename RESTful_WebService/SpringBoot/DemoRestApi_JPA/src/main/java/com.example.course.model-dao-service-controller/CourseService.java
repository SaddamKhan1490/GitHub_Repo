/**
  * Created by Saddam Khan on 05/31/2018.
  */
  
package com.example.course.model-dao-service-controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

	
	@Autowired
	private CourseRepository courseRepository;

	
	// HTTP GET method call
	public List<Course> getAllCourse(String topic_id){
		
		List Courses = new ArrayList();
		courseRepository.findByTopicId(topic_id).forEach(Courses::add);     // Connect to DataBase + Run a query to get all Courses + Convert each of those rows to Course instances and get them back
	
		return Courses;
		
	}
	
	// HTTP GET method call
	public Optional<Course> getCourse(String id) {
		
		return courseRepository.findById(id);				// Connect to DataBase + Run a query by making a call to the DataBase and returning a value
	
	}
	
	// HTTP POST method call
	public void addCourse(Course courses) {
		
		courseRepository.save(courses);						// Connect to DataBase + Run a query to save a Course to the database
	
	}
	
	// HTTP PUT method call
	public void updateCourse(Course courses) {
		
		courseRepository.save(courses);						// Connect to DataBase + Run a query to update and save a Course for given id to the database

	}
	
	// HTTP DELETE method call
	public void deleteCourse(String id) {
		
		courseRepository.deleteById(id);						// Connect to DataBase + Run a query to delete given id from the database

	}
	
	
}
