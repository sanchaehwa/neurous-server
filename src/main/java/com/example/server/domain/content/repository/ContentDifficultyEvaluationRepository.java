package com.example.server.domain.content.repository;

import com.example.server.domain.content.entity.ContentDifficultyEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentDifficultyEvaluationRepository extends JpaRepository<ContentDifficultyEvaluation, Integer> {
}
