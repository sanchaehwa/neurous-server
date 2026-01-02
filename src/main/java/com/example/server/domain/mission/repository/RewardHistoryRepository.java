package com.example.server.domain.mission.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.server.domain.mission.entity.RewardHistory;

public interface RewardHistoryRepository extends JpaRepository<RewardHistory, Long> {

	// 7일 연속으로 출석한 유저 조회하고 포인트 / 경험치 제공 내역
	@Modifying(clearAutomatically = true)
	@Query(value =
		"INSERT INTO reward_history (user_id, point, exp, reason, created_at, updated_at, deleted) " +
			"SELECT user_id, :rewardPoint, :rewardExp, 'ATTENDANCE_COMPLETED_THIS_WEEK', NOW(), NOW(), 0 " +
			"FROM users WHERE attendance_count >= 7",
		nativeQuery = true)
	void bulkInsertWeeklyRewardHistory(
		@Param("rewardPoint") int rewardPoint,
		@Param("rewardExp") int rewardExp
	);
}
