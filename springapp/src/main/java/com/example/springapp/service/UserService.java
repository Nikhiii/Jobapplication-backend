package com.example.springapp.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.example.springapp.model.User;

public interface UserService {

	public boolean registerUser(User user);
	
	public User getUserByEmail(String email);
	
	public UserDetails loadUserByUsername(String username);
}
