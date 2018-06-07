/**
  * Created by Saddam Khan on 5/14/2018.
  */
  
 package com.example.resource;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/hello")
@Api(value = "HelloWorld Resource", description = "shows hello world")          // @Api is the class level annotation for documentation at class level i.e. value = message
public class HelloResource {

    @ApiOperation(value = "Returns Hello World")                                // @ApiOperation is method level annotation for documentation at method level i.e. value = message
    @ApiResponses(																
            value = {
                    @ApiResponse(code = 100, message = "100 is the message"),   // @ApiResponse is HTTP method response level annotation for documentation per message call i.e. value = status_number or response_code, message
                    @ApiResponse(code = 200, message = "Successful Hello World")
            }
    )
    @GetMapping
    public String hello() {
        return "Hello World";
    }

    @ApiOperation(value = "Returns Hello World")
    @PostMapping("/post")
    public String helloPost(@RequestBody final String hello) {
        return hello;
    }

    @ApiOperation(value = "Returns Hello World")
    @PutMapping("/put")
    public String helloPut(@RequestBody final String hello) {
        return hello;
    }
}


