/**
  * Created by Saddam on 05/31/2018.
  */
 
  package com.example.unit_testing;

	import java.util.List;

  import org.apache.tomcat.jni.User;
  import org.springframework.http.MediaType;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PostMapping;
	import org.springframework.web.bind.annotation.RequestBody;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RestController;

	@RestController
	@RequestMapping("/all")
	public class SpringJpaController {
		
      private UserRepository userRepository;

      public void Resource (UserRepository userRepository) {
        this.userRepository = userRepository;
      }

      @GetMapping("/")
      public List<User> all() {
        return userRepository.findAll();
      }
	
  }
