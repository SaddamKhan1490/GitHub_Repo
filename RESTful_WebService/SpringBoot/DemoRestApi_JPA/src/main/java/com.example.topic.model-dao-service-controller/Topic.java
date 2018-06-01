
/**
  * Created by Saddam Khan on 05/31/2018.
  */
 
package com.example.topic.model-dao-service-controller.Topic;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Topic {
	
	// Variable and Object declaration
	
	@Id
	private String id;
	private String name;
	private String description;
	
	// Default Constructor
	public Topic() {
		
	}
	
	// Parameterised Constructor
	public Topic(String id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
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

}
