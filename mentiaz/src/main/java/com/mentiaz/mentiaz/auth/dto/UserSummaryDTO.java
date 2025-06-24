package com.mentiaz.mentiaz.auth.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSummaryDTO {
    private UUID id;
    private String username;
    private String firstName;
    private String lastName;
}
