package com.mentiaz.mentiaz.study_plan.service;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.mentiaz.mentiaz.auth.dto.UserSummaryDTO;
import com.mentiaz.mentiaz.auth.model.User;
import com.mentiaz.mentiaz.study_plan.dto.StudyPlanRequest;
import com.mentiaz.mentiaz.study_plan.dto.StudyPlanResponse;
import com.mentiaz.mentiaz.study_plan.model.StudyPlan;
import com.mentiaz.mentiaz.study_plan.repository.StudyPlanRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudyPlanService {
    private final StudyPlanRepository studyPlanRepository;

    public StudyPlanResponse createPlan(StudyPlanRequest request, User user) {
        StudyPlan plan = new StudyPlan();
        plan.setTitle(request.getTitle());
        plan.setDescription(request.getDescription());
        plan.setPublic(request.isPublic());
        plan.setOwner(user);

        StudyPlan saved = studyPlanRepository.save(plan);
        return toDto(saved);
    }

    public List<StudyPlanResponse> getPublicPlans() {
        return studyPlanRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public List<StudyPlanResponse> getPlansByUserId(UUID userId) {
        return studyPlanRepository.findByOwnerId(userId).stream()
                .map(this::toDto)
                .toList();
    }

    public List<StudyPlanResponse> getPublicPlansByUserId(UUID userId) {
        return studyPlanRepository.findByOwnerIdAndIsPublicTrue(userId).stream()
                .map(this::toDto)
                .toList();
    }

    public StudyPlanResponse getPlanById(UUID id) {
        StudyPlan plan = studyPlanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Study plan not found with id: " + id));

        return toDto(plan);
    }

    public StudyPlanResponse updatePlan(UUID id, StudyPlanRequest request, UUID userId) {
        StudyPlan plan = studyPlanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Study plan not found with id: " + id));

        if (!plan.getOwner().getId().equals(userId)) {
            throw new AccessDeniedException("You are not allowed to update this plan.");
        }

        plan.setTitle(request.getTitle());
        plan.setDescription(request.getDescription());

        return toDto(studyPlanRepository.save(plan));
    }

    public StudyPlanResponse updatePlanVisibility(UUID id, boolean isPublic, UUID userId) {
        StudyPlan plan = studyPlanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Study plan not found with id: " + id));

        if (!plan.getOwner().getId().equals(userId)) {
            throw new AccessDeniedException("You are not allowed to change visibility of this plan.");
        }

        plan.setPublic(isPublic);

        return toDto(studyPlanRepository.save(plan));
    }

    public StudyPlanResponse clonePlan(UUID originalId, User user) {
        StudyPlan original = studyPlanRepository.findById(originalId)
                .orElseThrow(() -> new EntityNotFoundException("Original plan not found"));

        StudyPlan copy = new StudyPlan();
        copy.setTitle("[Copy] " + original.getTitle());
        copy.setDescription(original.getDescription());
        copy.setPublic(false);
        copy.setOwner(user);
        copy.setOriginalId(original);

        return toDto(studyPlanRepository.save(copy));
    }

    public void deletePlan(UUID id, UUID userId) {
        StudyPlan plan = studyPlanRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Study plan not found with id: " + id));

        if (!plan.getOwner().getId().equals(userId))
            throw new AccessDeniedException("You are not allowed to delete this plan.");

        studyPlanRepository.delete(plan);
    }

    private StudyPlanResponse toDto(StudyPlan plan) {
        return new StudyPlanResponse(
                plan.getId(),
                plan.getTitle(),
                plan.getDescription(),
                plan.isPublic(),
                new UserSummaryDTO(
                        plan.getOwner().getId(),
                        plan.getOwner().getUsername(),
                        plan.getOwner().getFirstName(),
                        plan.getOwner().getLastName()),
                plan.getOriginalId() != null ? plan.getOriginalId().getId() : null,
                plan.getCreatedAt());
    }
}
