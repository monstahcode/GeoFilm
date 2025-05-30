package com.metrica.marzo25.geofilm.service;

import com.metrica.marzo25.geofilm.dto.request.LoginRequest;
import com.metrica.marzo25.geofilm.dto.request.RegisterRequest;
import com.metrica.marzo25.geofilm.dto.response.AuthResponse;
import com.metrica.marzo25.geofilm.dto.response.UserResponse;
import com.metrica.marzo25.geofilm.entity.User;
import com.metrica.marzo25.geofilm.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public ResponseEntity<AuthResponse> login(LoginRequest request) {
        try {
            Optional<User> existing = userRepository.findByEmail(request.getEmail());
            if (!existing.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthResponse(false, "El email especificado no existe"));
            }

            User foundUser = existing.get();

            if (passwordEncoder.matches(request.getPassword(), foundUser.getPassword())) {
                UserResponse userResponse = new UserResponse(
                        foundUser.getId(),
                        foundUser.getUsername(),
                        foundUser.getEmail()
                );
                return ResponseEntity.ok(new AuthResponse(true, "Usuario logeado exitosamente", userResponse));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthResponse(false, "Contraseña incorrecta"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponse(false, "Error interno del servidor"));
        }
    }

    public ResponseEntity<AuthResponse> register(RegisterRequest request) {
        try {
            Optional<User> existing = userRepository.findByUsername(request.getUsername());
            if (existing.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthResponse(false, "El nombre de usuario ya existe"));
            }

            existing = userRepository.findByEmail(request.getEmail());
            if (existing.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthResponse(false, "El email ya está registrado"));
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

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new AuthResponse(true, "Usuario registrado exitosamente", userResponse));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponse(false, "Error interno del servidor"));
        }
    }

    public ResponseEntity<AuthResponse> logout(String token) {
        // Implementar más tarde
        return ResponseEntity.ok(new AuthResponse(true, "Logout pendiente de implementar"));
    }
}