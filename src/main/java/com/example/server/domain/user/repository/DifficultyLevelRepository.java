package com.example.server.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.server.domain.user.entity.DifficultyLevel;
import com.example.server.domain.user.entity.vo.Level;

@Repository
public interface DifficultyLevelRepository extends JpaRepository<DifficultyLevel, Integer> {
	//사용자가 선택한 난이도의 설명 불러오기
	Optional<DifficultyLevel> findByLevel(Level level);
}
