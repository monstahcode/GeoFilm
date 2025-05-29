package com.metrica.marzo25.geofilm.service;

import com.metrica.marzo25.geofilm.dto.request.RegisterRequest;
import com.metrica.marzo25.geofilm.dto.response.AuthResponse;
import com.metrica.marzo25.geofilm.dto.response.UserResponse;
import com.metrica.marzo25.geofilm.entity.User;
import com.metrica.marzo25.geofilm.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AuthService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse register(RegisterRequest request) {
        Optional<User> existingUserByUsername = userRepository.findByUsername(request.getUsername());
        if (existingUserByUsername.isPresent()) {
            return new AuthResponse(false, "El nombre de usuario ya existe");
        }

        Optional<User> existingUserByEmail = userRepository.findByEmail(request.getEmail());
        if (existingUserByEmail.isPresent()) {
            return new AuthResponse(false, "El email ya está registrado");
        }

        User newUser = new User(
                request.getUsername(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
        );

        User savedUser = userRepository.save(newUser);

        UserResponse userResponse = new UserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail()
        );

        return new AuthResponse(true, "Usuario registrado exitosamente", userResponse);
    }

    public void logout(String token) {
        // Implementar más tarde
    }
}