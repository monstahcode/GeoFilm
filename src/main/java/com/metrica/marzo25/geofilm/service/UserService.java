package com.metrica.marzo25.geofilm.service;

import java.util.Optional;

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

    public Optional<User> getByEmail(String email){
        return repository.findByEmail(email);
    }
    
}
