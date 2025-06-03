package com.metrica.marzo25.geofilm.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.metrica.marzo25.geofilm.entity.Location;
import com.metrica.marzo25.geofilm.entity.User;
import com.metrica.marzo25.geofilm.exception.UserNotFoundException;
import com.metrica.marzo25.geofilm.exception.UserServiceException;
import com.metrica.marzo25.geofilm.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository repository;
    
    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> getByEmail(String email) {
    	if (email == null || email.isBlank()) {
    		throw new IllegalArgumentException("El email no puede estar vacío");
    	}
    	return repository.findByEmail(email);
    }
    
    public User getByEmailOrThrow(String email) {
        return getByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("No se encontró usuario con email: " + email));
    }
    
    public User saveUser(User user) {
    	if (user == null) {
    		throw new IllegalArgumentException("El usuario no puede ser nulo");
    	}
    	return repository.save(user);
    }
    
    public User saveUserLocation(User user, Location location) {
    	if (user == null) {
    		throw new IllegalArgumentException("El usuario no puede ser nulo");
    	}
    	if (location == null) {
    		throw new IllegalArgumentException("La ubicación no puede ser nula");
    	}

    	user.addFavoriteLocation(location);
    	return repository.save(user);
    }
    
    public Optional<User> getById(Long id) {
    	if (id == null) {
    		throw new IllegalArgumentException("El ID no puede ser nulo");
    	}
    	return repository.findById(id);
    }
    
    public User getByIdOrThrow(Long id) {
        return getById(id)
                .orElseThrow(() -> new UserNotFoundException("No se encontró usuario con ID: " + id));
    }
}