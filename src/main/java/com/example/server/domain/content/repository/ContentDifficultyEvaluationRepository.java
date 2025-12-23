package com.example.server.domain.content.repository;

import com.example.server.domain.content.entity.ContentDifficultyEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContentDifficultyEvaluationRepository extends JpaRepository<ContentDifficultyEvaluation, Integer> {
    Optional<ContentDifficultyEvaluation> findByUserIdAndContentId(int userId, int contentId);
}
