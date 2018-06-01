/**
  * Created by Saddam Khan on 05/31/2018.
  */
  
package com.example.course.model-dao-service-controller;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.example.topic.Topic;

@Entity
public class Course {
	
	// Variable and Object declaration
	
	@Id
	private String id;
	private String name;
	private String description;
	
	@ManyToOne
	private Topic topic;
	
	// Default Constructor
	public Course() {
		
	}
	
	// Parameterised Constructor
	public Course(String id, String name, String description, String topic_id) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.setTopic(new Topic(topic_id,"",""));
	}
	
	// Getter & Setters
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Topic getTopic() {
		return topic;
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}

	

}
