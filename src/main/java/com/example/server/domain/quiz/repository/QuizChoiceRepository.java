package com.example.server.domain.quiz.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.server.domain.quiz.entity.QuizChoice;

@Repository
public interface QuizChoiceRepository extends JpaRepository<QuizChoice, Integer> {

	Optional<QuizChoice> findByQuizIdAndChoiceNo(int quizId, int choiceNo);

	List<QuizChoice> findByQuizIdOrderByChoiceNoAsc(Integer quizId);

	Optional<QuizChoice> findByQuizIdAndIsCorrectTrue(Integer quizId);
}
