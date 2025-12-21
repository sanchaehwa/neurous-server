package com.example.server.domain.quiz.repository;

import com.example.server.domain.quiz.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
    Optional<Quiz> findByContentIdAndQuizDiff(Integer contentId, String quizDiff);
}
