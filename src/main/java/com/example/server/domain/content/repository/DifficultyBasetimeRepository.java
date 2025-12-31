package com.example.server.domain.content.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.server.domain.content.entity.DifficultyBasetime;

@Repository
public interface DifficultyBasetimeRepository extends JpaRepository<DifficultyBasetime, Long> {

	Optional<DifficultyBasetime> findByUserId(Long userId);

	// 보상 지급 후 기록 삭제 * 서비스 로직 확인
	void deleteByUserId(Long userId);
}
