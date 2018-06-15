/**
  * Created by Saddam on 04/18/2018
  */
  
package com.example.resource;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

@RestController
@RequestMapping("/rest/users")
public class UsersResource {

	TransportClient client;                                                                                     // TransportClient is provided by elasticsearch and we are using here in order to establish communication between our springboot application and port 9300 i.e. TransportClient server running on 9300

	public UsersResource() throws UnknownHostException {                                                        // Instance of TransportClient i.e. client will get created and bind with port 9300 instantly as soon as our controller gets started as we are initializing our client into our constructor of controller class
		client = new PreBuiltTransportClient(Settings.EMPTY)
				.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("localhost"), 9300));       // Specify IP_Address and Port number of Elasticsearch instance to which we want to bind our application i.e. localhost (ip_address of Elasticsearch) & 9300 (Transport Client port whereas 9200 will be Elasticsearch port) 

	}

	@GetMapping("/insert/{id}")
	public String insert(@PathVariable final String id) throws IOException {

		IndexResponse response = client.prepareIndex("employee", "id", id)                                        // Using client request will inject some data i.e. client.prepareIndex(), for employee index of JsonBuilder type object
				.setSource(jsonBuilder()
						.startObject()
						.field("name", "Ajay")
						.field("salary", 1200)
						.field("teamName", "Development")
						.endObject()
				)
				.get();
		return response.getResult().toString();
	}


	@GetMapping("/view/{id}")
	public Map<String, Object> view(@PathVariable final String id) {
		GetResponse getResponse = client.prepareGet("employee", "id", id).get();                                 // Using client request we can retrive i.e. client.prepareGet(), object from employee index of elasticsearch 
		System.out.println(getResponse.getSource());


		return getResponse.getSource();
	}

	@GetMapping("/update/{id}")
	public String update(@PathVariable final String id) throws IOException {

		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.index("employee")
				.type("id")
				.id(id)
				.doc(jsonBuilder()
						.startObject()
						.field("gender", "male")
						.endObject());
		try {
			UpdateResponse updateResponse = client.update(updateRequest).get();                                   // Using client request we can update i.e. client.update(), object from employee index of elasticsearch 
			System.out.println(updateResponse.status());
			return updateResponse.status().toString();
		} catch (InterruptedException | ExecutionException e) {
			System.out.println(e);
		}
		return "Exception";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable final String id) {

		DeleteResponse deleteResponse = client.prepareDelete("employee", "id", id).get();                    // Using client request we can delete i.e. client.prepareDelete(), employee index of from elasticsearch 

		System.out.println(deleteResponse.getResult().toString());
		return deleteResponse.getResult().toString();
	}
}
