package com.mentiaz.mentiaz.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    private String username;

    @Email
    private String email;

    @Size(min=8)
    private String password;

    @NotBlank
    private String firstName;
    
    @NotBlank
    private String lastName;

}
