package com.metrica.marzo25.geofilm.service;

import com.metrica.marzo25.geofilm.dto.request.LoginRequestDTO;
import com.metrica.marzo25.geofilm.dto.request.RegisterRequestDTO;
import com.metrica.marzo25.geofilm.dto.response.AuthResponseDTO;
import com.metrica.marzo25.geofilm.dto.response.UserResponseDTO;
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

    public ResponseEntity<AuthResponseDTO> login(LoginRequestDTO request) {
        try {
            Optional<User> existing = userRepository.findByEmail(request.getEmail());
            if (!existing.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthResponseDTO(false, "El email especificado no existe"));
            }

            User foundUser = existing.get();

            if (passwordEncoder.matches(request.getPassword(), foundUser.getPassword())) {
                UserResponseDTO userResponse = new UserResponseDTO(
                        foundUser.getId(),
                        foundUser.getUsername(),
                        foundUser.getEmail()
                );
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new AuthResponseDTO(true, "Usuario logeado exitosamente", userResponse));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthResponseDTO(false, "Contraseña incorrecta"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponseDTO(false, "Error interno del servidor"));
        }
    }

    public ResponseEntity<AuthResponseDTO> register(RegisterRequestDTO request) {
        try {
            Optional<User> existing = userRepository.findByUsername(request.getUsername());
            if (existing.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthResponseDTO(false, "El nombre de usuario ya existe"));
            }

            existing = userRepository.findByEmail(request.getEmail());
            if (existing.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new AuthResponseDTO(false, "El email ya está registrado"));
            }

            User newUser = new User(
                    request.getUsername(),
                    request.getEmail(),
                    passwordEncoder.encode(request.getPassword())
            );

            User savedUser = userRepository.save(newUser);

            UserResponseDTO userResponse = new UserResponseDTO(
                    savedUser.getId(),
                    savedUser.getUsername(),
                    savedUser.getEmail()
            );

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new AuthResponseDTO(true, "Usuario registrado exitosamente", userResponse));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AuthResponseDTO(false, "Error interno del servidor"));
        }
    }

    public ResponseEntity<AuthResponseDTO> logout(String token) {
        // Implementar más tarde. Igual lo tiene que hacer el front?
        return ResponseEntity.ok(new AuthResponseDTO(true, "Logout pendiente de implementar"));
    }
}