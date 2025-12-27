package com.example.server.domain.quiz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.server.domain.quiz.entity.QuizSolve;

@Repository
public interface QuizSolveRepository extends JpaRepository<QuizSolve, Long> {
	Optional<QuizSolve> findByUserIdAndContentId(Long userId, int contentId);
}
