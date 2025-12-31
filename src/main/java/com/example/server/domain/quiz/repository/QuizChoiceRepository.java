package com.example.server.domain.quiz.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.server.domain.quiz.entity.QuizChoice;

@Repository
public interface QuizChoiceRepository extends JpaRepository<QuizChoice, Long> {

	Optional<QuizChoice> findByQuiz_QuizIdAndChoiceNo(Long quizId, int choiceNo);

	List<QuizChoice> findByQuiz_QuizIdOrderByChoiceNoAsc(Long quizId);

	Optional<QuizChoice> findByQuiz_QuizIdAndIsCorrectTrue(Long quizId);
}
