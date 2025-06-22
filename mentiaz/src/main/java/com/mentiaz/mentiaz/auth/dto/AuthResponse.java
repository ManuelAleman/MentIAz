package com.mentiaz.mentiaz.auth.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private UUID id;
    private String username;
    private String email;
    private String token;    
}
