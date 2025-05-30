package com.metrica.marzo25.geofilm.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metrica.marzo25.geofilm.entity.Location;
import com.metrica.marzo25.geofilm.entity.User;
import com.metrica.marzo25.geofilm.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository repository;
    
    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> getByEmail(String email){
        return repository.findByEmail(email);
    }
    
    public User saveUser(User user) {
    	return repository.save(user);
    }
    
    public User saveUserLocation(User user, Location location) {
    	user.addFavoriteLocation(location);
    	return repository.save(user);
    }
    
    public Optional<User> getById(Long id){
    	return repository.findById(id);
    }
    
}
