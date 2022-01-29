package com.stackroute.keepnote;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.stackroute.keepnote.jwtfilter.JwtFilter;

/*
 * The @SpringBootApplication annotation is equivalent to using @Configuration, @EnableAutoConfiguration 
 * and @ComponentScan with their default attributes
 */

@SpringBootApplication
public class NoteServiceApplication {

	
	
	/*
	 * Define the bean for Filter registration. Create a new FilterRegistrationBean
	 * object and use setFilter() method to set new instance of JwtFilter object.
	 * Also specifies the Url patterns for registration bean.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	  @Bean
	    public FilterRegistrationBean jwtFilter() {
		  FilterRegistrationBean registration = new FilterRegistrationBean();
	        registration.setFilter(new JwtFilter());
	        registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
	        registration.addUrlPatterns("/api/v1/note");
	        registration.setOrder(1);
	        return registration;
	    }
	
	/*
	 * 
	 * You need to run SpringApplication.run, because this method start whole spring
	 * framework. Code below integrates your main() with SpringBoot
	 */

	public static void main(String[] args) {
		SpringApplication.run(NoteServiceApplication.class, args);
	}
}
