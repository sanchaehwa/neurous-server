package com.example.server.domain.quiz.repository;

import com.example.server.domain.quiz.entity.QuizChoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizChoiceRepository extends JpaRepository<QuizChoice, Integer> {

    List<QuizChoice> findByQuizIdOrderByChoiceNoAsc(Integer quizId);

    Optional<QuizChoice> findByQuizIdAndIsCorrectTrue(Integer quizId);
}
