package com.mentiaz.mentiaz.study_plan.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudyPlanSummaryDTO {
    private UUID id;
    private String title;
    private boolean isPublic;
    private String ownerUsername;
}

