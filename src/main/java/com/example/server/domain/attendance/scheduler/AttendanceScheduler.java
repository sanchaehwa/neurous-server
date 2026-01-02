package com.example.server.domain.attendance.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.server.domain.reward.repository.RewardHistoryRepository;
import com.example.server.domain.reward.service.command.PointExperienceProvisionInformation;
import com.example.server.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttendanceScheduler {

	private final UserRepository userRepository;
	private final RewardHistoryRepository rewardHistoryRepository;

	/**
	 * 매주 월요일 00:00시 확인
	 */
	@Transactional
	@Scheduled(cron = "0 0 0 * * MON", zone = "Asia/Seoul")
	public void checkAttendanceForOneWeekAndResetAttendanceCount() {

		log.info("주간 출석 보상 및 초기화 프로세스 시작");

		int rewardPoint = PointExperienceProvisionInformation.ATTENDANCE_ALL_WEEK_POINT;
		int rewardExp = PointExperienceProvisionInformation.ATTENDANCE_ALL_WEEK_EXP;

		//포인트 * 경험치 제공 내역 저장
		rewardHistoryRepository.bulkInsertWeeklyRewardHistory(rewardPoint, rewardExp);

		//포인트 * 경험치 제공
		int numberOfUpdateUsers = userRepository.rewardAttendanceAllWeek(rewardPoint, rewardExp);

		//새로운 주간을 위해 0 초기화
		userRepository.resetAllAttendance();

		log.info("주간 보상 완료: {}명 지급", numberOfUpdateUsers);
	}
}
