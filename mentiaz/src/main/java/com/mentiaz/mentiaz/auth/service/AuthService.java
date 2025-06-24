package com.mentiaz.mentiaz.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mentiaz.mentiaz.auth.dto.AuthResponse;
import com.mentiaz.mentiaz.auth.dto.LoginRequest;
import com.mentiaz.mentiaz.auth.dto.RegisterRequest;
import com.mentiaz.mentiaz.auth.model.User;
import com.mentiaz.mentiaz.auth.repository.UserRepository;
import com.mentiaz.mentiaz.auth.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request){
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        userRepository.save(user);

        String token = jwtService.generateToken(user);

        return new AuthResponse(user.getId(), user.getUsername(), user.getEmail(), token);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user);

        return new AuthResponse(user.getId(), user.getUsername(), user.getEmail(), token);
    }
}
