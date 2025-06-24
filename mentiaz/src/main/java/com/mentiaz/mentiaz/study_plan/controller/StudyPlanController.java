package com.mentiaz.mentiaz.study_plan.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mentiaz.mentiaz.auth.model.User;
import com.mentiaz.mentiaz.study_plan.dto.StudyPlanRequest;
import com.mentiaz.mentiaz.study_plan.dto.StudyPlanResponse;
import com.mentiaz.mentiaz.study_plan.service.StudyPlanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static com.mentiaz.mentiaz.util.ResponseUtil.*;

@Controller
@RequestMapping("/api/study-plans")
@RequiredArgsConstructor
public class StudyPlanController {
    private final StudyPlanService studyPlanService;

    @PostMapping
    public ResponseEntity<StudyPlanResponse> create(@Valid @RequestBody StudyPlanRequest request, @AuthenticationPrincipal User user){
        return created(studyPlanService.createPlan(request, user));
    }
}
