/**
  * Created by Saddam Khan on 5/14/2018.
  */
 
 package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@EnableSwagger2
@Configuration
public class SwaggerConfig {


    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.resource"))			// Will scan the base package and will create associated API's only for those packages under swagger, else swagger will create API's almost for all endpoints which are avilable under the project 
                .paths(regex("/rest.*"))													// Used for applying further filtering of REST enpoints i.e. API's will be created only for those REST endpoints which fall under specified REGEX pattern
                .build()
                .apiInfo(metaInfo());
    }

    private ApiInfo metaInfo() {

        ApiInfo apiInfo = new ApiInfo(
                "Spring Boot Swagger Example API",
                "Spring Boot Swagger Example API for Youtube",
                "1.0",
                "Terms of Service",
                new Contact("xxxx", "https://www.youtube.com/",
                        "xxxx@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licesen.html"
        );

        return apiInfo;
    }
}
