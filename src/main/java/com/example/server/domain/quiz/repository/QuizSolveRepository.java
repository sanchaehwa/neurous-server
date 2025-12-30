package com.example.server.domain.quiz.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.server.domain.quiz.entity.QuizSolve;

@Repository
public interface QuizSolveRepository extends JpaRepository<QuizSolve, Long> {

	Optional<QuizSolve> findByUser_IdAndReadContent_ReadContentId(Long userId, Long readContentId);

	//특정 읽기 기록에 대한 풀이 이력이 있는지 확인
	boolean existsByReadContent_ReadContentId(Long readContentId);
}
