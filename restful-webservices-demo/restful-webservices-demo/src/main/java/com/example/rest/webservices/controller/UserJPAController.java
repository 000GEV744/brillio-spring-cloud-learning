package com.example.rest.webservices.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.rest.webservices.exception.UserNotFoundException;
import com.example.rest.webservices.model.Post;
import com.example.rest.webservices.model.User;
import com.example.rest.webservices.services.PostRepository;
import com.example.rest.webservices.services.UserDaoServices;
import com.example.rest.webservices.services.UserRepository;

@RestController
@RequestMapping("/jpa")
public class UserJPAController {

	@Autowired
	private UserDaoServices userService;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@GetMapping("/user")
	public List<User> getAllUser(){
		return userRepository.findAll();
	}
	
	@PostMapping("/user")
	public ResponseEntity<Object> saveUser(@Valid @RequestBody User user) {
		User createdUser = userRepository.save(user);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(createdUser.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/user/{id}")
	public EntityModel<User> getUser(@PathVariable int id) {
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFoundException("id - "+id);
		}
		
		/********START :: Instead of Hardcoding the link to the user we are 
		 attaching the link specific to the method getAllUser()*********/
		WebMvcLinkBuilder linkToUsers = 
				WebMvcLinkBuilder.
				linkTo(methodOn(this.getClass()).getAllUser()); //attaching the link specific to getAllUser()
		EntityModel<User> model = EntityModel.of(user.get());
		model.add(linkToUsers.withRel("all-users")); //we will add a relation for every link we add in
		/*********END*********/
		return model;
	} 
	
	@DeleteMapping("/user/{id}")
	public void deleteUser(@PathVariable int id){
		userRepository.deleteById(id);
	}
	
	//get all the posts of an User
	@GetMapping("/user/{id}/posts")
	public List<Post> getUserPost(@PathVariable int id){
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFoundException("id-"+id);
		}
		return user.get().getPost();
	}
	
	//let's create a post for a user
	@PostMapping("/user/{id}/posts")
	public ResponseEntity<Object> CreateUserPost(@PathVariable int id, @RequestBody Post post) {
		
		Optional<User> user = userRepository.findById(id);
		
		if(!user.isPresent()) {
			throw new UserNotFoundException("id-"+id);
		}
		
		post.setUser(user.get());
		postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(post.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
}
