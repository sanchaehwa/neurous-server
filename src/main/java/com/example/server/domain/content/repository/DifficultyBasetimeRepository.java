package com.example.server.domain.content.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.server.domain.content.entity.DifficultyBasetime;

@Repository
public interface DifficultyBasetimeRepository extends JpaRepository<DifficultyBasetime, Integer> {
	Optional<DifficultyBasetime> findTopByUserIdOrderByBaseTimeDesc(Long userId);
}
