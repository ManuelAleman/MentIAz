package com.mentiaz.mentiaz.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mentiaz.mentiaz.auth.dto.AuthResponse;
import com.mentiaz.mentiaz.auth.dto.LoginRequest;
import com.mentiaz.mentiaz.auth.dto.RegisterRequest;
import com.mentiaz.mentiaz.auth.dto.UserSummaryDTO;
import com.mentiaz.mentiaz.auth.model.User;
import com.mentiaz.mentiaz.auth.service.AuthService;
import static com.mentiaz.mentiaz.util.ResponseUtil.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ok(response);
    }
    
    @GetMapping("/me")
    public ResponseEntity<UserSummaryDTO> getCurrentUser(@AuthenticationPrincipal User user) {
    UserSummaryDTO dto = new UserSummaryDTO(
        user.getId(),
        user.getUsername(),
        user.getFirstName(),
        user.getLastName()
    );
    return ok(dto);
}
    
    
}
