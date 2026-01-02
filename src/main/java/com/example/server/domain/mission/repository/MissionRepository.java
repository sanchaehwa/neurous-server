package com.example.server.domain.mission.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.server.domain.mission.entity.Mission;
import com.example.server.domain.mission.entity.vo.MissionType;
import com.example.server.domain.user.entity.User;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {
	// 특정 유저의 모든 미션(퀴즈, 읽기 등)을 가져옵니다.
	List<Mission> findAllByUser(User user);

	// 특정 유저의 특정 타입 미션만 가져올 때
	Optional<Mission> findByUserAndMissionType(User user, MissionType missionType);
}
