package com.example.rest.webservices.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.rest.webservices.model.User;

@Component
public class UserDaoServices {

	private static List<User> userList = new ArrayList<>();
	private static int userCount = 5;
	
	static {
		userList.add(new User(1,"Adam",new Date()));
		userList.add(new User(2,"Lexa",new Date()));
		userList.add(new User(3,"Cydina",new Date()));
		userList.add(new User(4,"Erang",new Date()));
		userList.add(new User(5,"Alfie",new Date()));
		
	}
	
	//get all users
	public List<User> findAll(){
		return userList;
	}
	
	//get a particular User
	public User getUser(int id) {
		for(User user: userList) {
			if(user.getId()==id) {
				return user;
			}
		}
		return null;
	}
	
	//save a user and return that user after saving
	public User save(User user) {
		user.setId(++userCount);
		userList.add(user);
		return user;
	}
	
	// delete by ID
	public User deleteById(int id) {
		Iterator<User> itr = userList.iterator();
		while(itr.hasNext()) {
			User user = itr.next();
			if(user.getId() == id) {
				itr.remove();
				return user;
			}
		}
		return null;
	}
}

