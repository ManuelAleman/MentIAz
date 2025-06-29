package com.mentiaz.mentiaz.study_plan.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mentiaz.mentiaz.auth.model.User;
import com.mentiaz.mentiaz.study_plan.dto.StudyPlanRequest;
import com.mentiaz.mentiaz.study_plan.dto.StudyPlanResponse;
import com.mentiaz.mentiaz.study_plan.dto.UpdateVisibilityRequest;
import com.mentiaz.mentiaz.study_plan.service.StudyPlanService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import static com.mentiaz.mentiaz.util.ResponseUtil.*;

import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;

@Controller
@RequestMapping("/api/study-plans")
@RequiredArgsConstructor
public class StudyPlanController {
    private final StudyPlanService studyPlanService;

    @PostMapping
    public ResponseEntity<StudyPlanResponse> create(@Valid @RequestBody StudyPlanRequest request,
            @AuthenticationPrincipal User user) {
        return created(studyPlanService.createPlan(request, user));
    }

    @GetMapping("/public")
    public ResponseEntity<List<StudyPlanResponse>> getPublicPlans() {
        return ok(studyPlanService.getPublicPlans());
    }

    @GetMapping("/my")
    public ResponseEntity<List<StudyPlanResponse>> getMyPlans(@AuthenticationPrincipal User user) {
        return ok(studyPlanService.getPlansByUserId(user.getId()));
    }

    @GetMapping("/public/user/{userId}")
    public ResponseEntity<List<StudyPlanResponse>> getPublicPlansByUser(@PathVariable UUID userId) {
        return ok(studyPlanService.getPublicPlansByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudyPlanResponse> getById(@PathVariable UUID id) {
        return ok(studyPlanService.getPlanById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudyPlanResponse> update(@PathVariable UUID id,
            @Valid @RequestBody StudyPlanRequest request,
            @AuthenticationPrincipal User user) {
        return ok(studyPlanService.updatePlan(id, request, user.getId()));
    }

    @PatchMapping("/{id}/visibility")
    public ResponseEntity<StudyPlanResponse> updateVisibility(
            @PathVariable UUID id,
            @RequestBody UpdateVisibilityRequest request,
            @AuthenticationPrincipal User user) {
        return ok(studyPlanService.updatePlanVisibility(id, request.isPublic(), user.getId()));
    }

    @PostMapping("/{id}/clone")
    public ResponseEntity<StudyPlanResponse> clone(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        return created(studyPlanService.clonePlan(id, user));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        studyPlanService.deletePlan(id, user.getId());
        return noContent();
    }

}
