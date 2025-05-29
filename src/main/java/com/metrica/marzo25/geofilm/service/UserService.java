package com.metrica.marzo25.geofilm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metrica.marzo25.geofilm.entity.User;
import com.metrica.marzo25.geofilm.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository repository;
	
	@Autowired
	public UserService(UserRepository repository) {
		this.repository = repository;
	}

	public User login(String email, String password) {
		User user = repository.loginWithEmailAndPassword(email,password);
		return user;
	}
	
	public User register(String email, String password, String username) {
		User user = repository.registerUser(email, password, username);
		return user;
	}
	
}
