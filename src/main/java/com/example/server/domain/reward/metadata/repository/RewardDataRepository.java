package com.example.server.domain.reward.metadata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.server.domain.reward.metadata.entity.RewardData;

@Repository
public interface RewardDataRepository extends JpaRepository<RewardData, Long> {
}
