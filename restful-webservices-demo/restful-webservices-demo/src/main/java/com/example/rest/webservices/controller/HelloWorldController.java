package com.example.rest.webservices.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rest.webservices.model.Student;

@RestController
@RequestMapping("/")
public class HelloWorldController {
	

	@Autowired
	private MessageSource messageSource;
	
	@GetMapping("/hello-world")
	public String helloWorld() {
		return "hello World!";
	}
	
	@GetMapping("/student-bean")
	public Student getStudent() {
		return new Student("Anuj","singh","anuj60360@gmail.com");
	}
	
	@GetMapping("/student-bean/{name}")
	public Student getStudentWithName(@PathVariable String name) {
		return new Student(name,"singh","anuj60360@gmail.com");
	}
	

	@GetMapping("/hello-world-internationalization")
	public String helloWorldInternationalization(
	/* @RequestHeader(name = "Accept-Language", required=false) Locale locale */){
		//now we want to return the resource based on the locale.
		//we need to configure the values for different locale.
		//for ex: en-Hello world
		//		  nl-Goede Morgen
		//        fr-bonjour so we will create a property file and pick there information from there.
		
		return messageSource.getMessage("good.morning.message",null,"Default-Message", LocaleContextHolder.getLocale());
        //return "Good Morning!";
	}
}

