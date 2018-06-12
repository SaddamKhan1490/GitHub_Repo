/**
  * Created by Saddam on 05/14/2018
  */

package com.example.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/hello")
public class HelloResource {

    @GetMapping("/check")
    public String check() {

        return "Check Worked";
    }
}
