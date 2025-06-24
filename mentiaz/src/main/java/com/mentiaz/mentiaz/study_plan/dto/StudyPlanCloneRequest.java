package com.mentiaz.mentiaz.study_plan.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class StudyPlanCloneRequest {
    private UUID originalId;
    private boolean makePublic;
}
