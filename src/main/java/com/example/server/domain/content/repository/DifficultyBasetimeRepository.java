package com.example.server.domain.content.repository;

import com.example.server.domain.content.entity.DifficultyBasetime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DifficultyBasetimeRepository extends JpaRepository<DifficultyBasetime, Integer> {
    Optional<DifficultyBasetime> findTopByUserIdOrderByBaseTimeDesc(Long userId);
}
