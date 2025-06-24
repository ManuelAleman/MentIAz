package com.mentiaz.mentiaz.study_plan.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mentiaz.mentiaz.study_plan.model.StudyPlan;

public interface StudyPlanRepository extends JpaRepository<StudyPlan, UUID> {
    List<StudyPlan> findByOwnerId(UUID ownerId);

    List<StudyPlan> findByIsPublicTrue();

    List<StudyPlan> findByOwnerIdAndIsPublicTrue(UUID ownerId);
}
