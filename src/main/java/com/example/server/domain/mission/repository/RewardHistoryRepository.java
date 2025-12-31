package com.example.server.domain.mission.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.server.domain.mission.entity.RewardHistory;

public interface RewardHistoryRepository extends JpaRepository<RewardHistory, Long> {

}
