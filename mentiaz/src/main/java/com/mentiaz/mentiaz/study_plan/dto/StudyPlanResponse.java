package com.mentiaz.mentiaz.study_plan.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.mentiaz.mentiaz.auth.dto.UserSummaryDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudyPlanResponse {
    private UUID id;
    private String title;
    private String description;
    private boolean isPublic;
    private UserSummaryDTO owner;
    private UUID originalId; 
    private LocalDateTime createdAt;
}
