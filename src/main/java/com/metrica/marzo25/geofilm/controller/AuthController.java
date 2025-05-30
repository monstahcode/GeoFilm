package com.metrica.marzo25.geofilm.controller;

import com.metrica.marzo25.geofilm.dto.request.LoginRequestDTO;
import com.metrica.marzo25.geofilm.dto.request.RegisterRequestDTO;
import com.metrica.marzo25.geofilm.dto.response.AuthResponseDTO;
import com.metrica.marzo25.geofilm.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        return authService.login(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<AuthResponseDTO> logout(@RequestHeader("Authorization") String token) {
        return authService.logout(token);
    }
}