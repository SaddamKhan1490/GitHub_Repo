/**
  * Created by Saddam on 05/24/2018
  */
  

package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.LdapShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;

import java.util.Arrays;

@EnableGlobalMethodSecurity                                                             // @EnableGlobalMethodSecurity will enable spring security into our application
@Configuration                                                                          // @Configuration tell spring boot to load the associted class definition before actually running application i.e. during config loading
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {               // All the security configuration is provided by WebSecurityConfigurerAdapter

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.anyRequest().fullyAuthenticated()
				.and()
				.formLogin();
	}


	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {         // AuthenticationManagerBuilder will do all the configuration which is necessary for LDAP
		auth
				.ldapAuthentication()                                                         // Default spring security context is created based on the given LDAP
				.userDnPatterns("uid={0},ou=people")
				.groupSearchBase("ou=groups")
				.contextSource(contextSource())
				.passwordCompare()
				.passwordEncoder(new LdapShaPasswordEncoder())
				.passwordAttribute("userPassword");
	}

	@Bean
	public DefaultSpringSecurityContextSource contextSource() {                        // Default spring security context based on LDAP URL					
		return new DefaultSpringSecurityContextSource(Arrays.asList("ldap://localhost:8389/"), "dc=springframework,dc=org");
	}
}
