package com.metrica.marzo25.geofilm.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metrica.marzo25.geofilm.entity.Location;
import com.metrica.marzo25.geofilm.entity.User;
import com.metrica.marzo25.geofilm.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*")
public class UserController {
	
	private UserService service;

	@Autowired
	public UserController(UserService service) {
		this.service = service;
	}
	
	@GetMapping("/email")
	public Optional<User> getByEmeail(@RequestBody String email){
		return service.getByEmail(email);
	}
	
	@GetMapping("/id")
	public Optional<User> getById(@RequestBody Long id){
		return service.getById(id);
	}
	
	@PostMapping
	public User saveLocation(@RequestBody User user, @RequestBody Location location) {
		return service.saveUserLocation(user, location);
	}
	
	public User saveUser(User user) {
		return service.saveUser(user);
	}
	
}
