/**
  * Created by Saddam on 05/14/2018
  */
  
package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity                                                                    // @EnableWebSecurity will enable spring security into our application
@Configuration                                                                        // @Configuration tell spring boot to load the associted class definition before actually running application i.e. during config loading.
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {             // WebSecurityCofigurerAdapter Class : place where all Spring Security level related injection happens

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()                                                 // auth.inMemoryAuthentication() under AuthenticationManagerBuilder we are configuring user and associated user role # Here instead of using inmemory storage we can go for RDBMS itself for fetching user details

                .withUser("user1").password("test1").roles("USER").and()
                .withUser("user2").password("test2").roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {            // WebSecurityCofigurerAdapter has a configure()method inside which we specify all the avilable security option i.e.authorizeRequest-antMatcher-anyRequest-permitAll-fullyAuthenticated-etc....

        httpSecurity                                                                  // HttpSecurity object contains all the methods associated with Spring Security for RESTful web_service
                .authorizeRequests()
                .anyRequest()
                .fullyAuthenticated()
                //.antMatchers("**/rest/*")                                           // antMatcher("*/hello").hasRole("USER") will allow the access of URL ending with "/hello" only to users who have role as "USER" # This is simple authentication check
                .and()
                //addFilterBefore(customFilter(), BasicAuthenticationFilter.class)
                .httpBasic();
        httpSecurity.csrf().disable();                                                 // http.Security.csrf used for enabling or disabling cross site forgery alert i.e. security while navigating betweeen multiple sites over the browser after login to our application where we have configured spring security


    }

    @Bean
    public CustomFilter customFilter() {
        return new CustomFilter();
    }
    
}
