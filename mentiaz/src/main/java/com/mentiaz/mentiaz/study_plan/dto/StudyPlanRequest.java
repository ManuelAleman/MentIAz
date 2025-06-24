package com.mentiaz.mentiaz.study_plan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class StudyPlanRequest {
    @NotBlank
    @Size(max=100)
    private String title;

    @NotBlank
    private String description;

    @JsonProperty("isPublic")
    private boolean isPublic;
}
