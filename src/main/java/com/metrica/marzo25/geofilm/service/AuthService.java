package com.metrica.marzo25.geofilm.service;

import com.metrica.marzo25.geofilm.dto.request.LoginRequestDTO;
import com.metrica.marzo25.geofilm.dto.request.RegisterRequestDTO;
import com.metrica.marzo25.geofilm.dto.response.AuthResponseDTO;
import com.metrica.marzo25.geofilm.dto.response.UserResponseDTO;

import com.metrica.marzo25.geofilm.entity.User;

import com.metrica.marzo25.geofilm.exception.InvalidCredentialsException;
import com.metrica.marzo25.geofilm.exception.UserAlreadyExistsException;
import com.metrica.marzo25.geofilm.exception.UserNotFoundException;

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
		Optional<User> existing = userRepository.findByEmail(request.getEmail());
		if (!existing.isPresent()) {
			throw new UserNotFoundException("El email especificado no existe");
		}

		User foundUser = existing.get();

		if (!passwordEncoder.matches(request.getPassword(), foundUser.getPassword())) {
			throw new InvalidCredentialsException("Contraseña incorrecta");
		}

		UserResponseDTO userResponse = new UserResponseDTO(
				foundUser.getUsername(),
				foundUser.getEmail()
				);
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(new AuthResponseDTO(true, "Usuario logeado exitosamente", userResponse));
	}

	public ResponseEntity<AuthResponseDTO> register(RegisterRequestDTO request) {
		Optional<User> existing = userRepository.findByUsername(request.getUsername());
		if (existing.isPresent()) {
			throw new UserAlreadyExistsException("El nombre de usuario ya existe");
		}

		existing = userRepository.findByEmail(request.getEmail());
		if (existing.isPresent()) {
			throw new UserAlreadyExistsException("El email ya está registrado");
		}

		User newUser = new User(
				request.getUsername(),
				request.getEmail(),
				passwordEncoder.encode(request.getPassword())
				);

		User savedUser = userRepository.save(newUser);

		UserResponseDTO userResponse = new UserResponseDTO(
				savedUser.getUsername(),
				savedUser.getEmail()
				);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new AuthResponseDTO(true, "Usuario registrado exitosamente", userResponse));
	}
}