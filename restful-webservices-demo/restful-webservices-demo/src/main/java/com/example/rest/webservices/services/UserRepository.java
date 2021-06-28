package com.example.rest.webservices.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.rest.webservices.model.User;

@Repository
public interface UserRepository   extends JpaRepository<User, Integer>{

}
