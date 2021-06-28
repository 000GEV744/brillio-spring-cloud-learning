package com.example.rest.webservices.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.rest.webservices.exception.UserNotFoundException;
import com.example.rest.webservices.model.User;
import com.example.rest.webservices.services.UserDaoServices;

@RestController
@RequestMapping("/api")
public class UserController {
	
	@Autowired
	private UserDaoServices userService;

	@GetMapping("/user")
	public List<User> getAllUser(){
		return userService.findAll();
	}
	
	@PostMapping("/user")
	public ResponseEntity<Object> saveUser(@Valid @RequestBody User user) {
		User createdUser = userService.save(user);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(createdUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/user/{id}")
	public EntityModel<User> getUser(@PathVariable int id) {
		User user = userService.getUser(id);
		if(user == null) {
			throw new UserNotFoundException("id - "+id);
		}
		
		/********START :: Instead of Hardcoding the link to the user we are 
		 attaching the link specific to the method getAllUser()*********/
		WebMvcLinkBuilder linkToUsers = 
				WebMvcLinkBuilder.
				linkTo(methodOn(this.getClass()).getAllUser()); //attaching the link specific to getAllUser()
		EntityModel<User> model = EntityModel.of(user);
		model.add(linkToUsers.withRel("all-users")); //we will add a relation for every link we add in
		/*********END*********/
		return model;
	}
	
	@DeleteMapping("/user/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable int id){
		User user = userService.deleteById(id);
		if(user == null) {
			throw new UserNotFoundException("id - "+id);
		}
		return ResponseEntity.noContent().build();
	}
}
