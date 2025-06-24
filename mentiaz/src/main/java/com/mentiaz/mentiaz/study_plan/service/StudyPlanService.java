package com.mentiaz.mentiaz.study_plan.service;

import org.springframework.stereotype.Service;

import com.mentiaz.mentiaz.auth.dto.UserSummaryDTO;
import com.mentiaz.mentiaz.auth.model.User;
import com.mentiaz.mentiaz.study_plan.dto.StudyPlanRequest;
import com.mentiaz.mentiaz.study_plan.dto.StudyPlanResponse;
import com.mentiaz.mentiaz.study_plan.model.StudyPlan;
import com.mentiaz.mentiaz.study_plan.repository.StudyPlanRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudyPlanService {
    private final StudyPlanRepository studyPlanRepository;

    public StudyPlanResponse createPlan(StudyPlanRequest request, User user){
        StudyPlan plan = new StudyPlan();
        plan.setTitle(request.getTitle());
        plan.setDescription(request.getDescription());
        plan.setPublic(request.isPublic());
        plan.setOwner(user);

        StudyPlan saved = studyPlanRepository.save(plan);
        return toDto(saved);
    }


    private StudyPlanResponse toDto(StudyPlan plan){
        return new StudyPlanResponse(
            plan.getId(), 
            plan.getTitle(), 
            plan.getDescription(), 
            plan.isPublic(), 
            new UserSummaryDTO(
                plan.getOwner().getId(), 
                plan.getOwner().getUsername(), 
                plan.getOwner().getFirstName(), 
                plan.getOwner().getLastName()
                ), 
                plan.getOriginalId() != null ? plan.getOriginalId().getId() : null, 
                plan.getCreatedAt());
    }
}
