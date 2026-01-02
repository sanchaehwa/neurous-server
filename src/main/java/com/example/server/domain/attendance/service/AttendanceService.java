package com.example.server.domain.attendance.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.server.domain.attendance.entity.Attendance;
import com.example.server.domain.attendance.repository.AttendanceRepository;
import com.example.server.domain.mission.entity.RewardHistory;
import com.example.server.domain.mission.entity.vo.HistoryMessage;
import com.example.server.domain.mission.repository.RewardHistoryRepository;
import com.example.server.domain.mission.service.command.PointExperienceProvisionInformation;
import com.example.server.domain.user.entity.User;
import com.example.server.domain.user.repository.UserRepository;
import com.example.server.global.exception.message.ErrorMessage;
import com.example.server.global.exception.model.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AttendanceService {

	private final UserRepository userRepository;
	private final RewardHistoryRepository rewardHistoryRepository;
	private final AttendanceRepository attendanceRepository;

	private static final int TODAY_ATTENDANCE_REWARD_POINT = PointExperienceProvisionInformation.ATTENDANCE_ONE_DAY_POINT;
	private static final int TODAY_ATTENDANCE_REWARD_EXP = PointExperienceProvisionInformation.ATTENDANCE_ONE_DAY_EXP;

	// 당일 출석 리워드 지급 (앱 접속하면 자동으로)
	@Transactional
	public void providedAttendanceRewardToday(LocalDateTime attendanceTime, Long userId) {

		boolean isAlreadyAttended = attendanceRepository.existsByUserIdAndAttendanceDate(
			userId, attendanceTime.toLocalDate()
		);

		if (isAlreadyAttended)
			return;

		User user = findUserById(userId);

		attendanceRepository.save(Attendance.create(user, attendanceTime.toLocalDate()));

		user.addPointAndExp(TODAY_ATTENDANCE_REWARD_POINT, TODAY_ATTENDANCE_REWARD_EXP);
		user.updateAttendanceForOneWeek(attendanceTime);

		RewardHistory rewardHistory = RewardHistory.create(
			user,
			TODAY_ATTENDANCE_REWARD_POINT,
			TODAY_ATTENDANCE_REWARD_EXP,
			HistoryMessage.ATTENDANCE_COMPLETED_TODAY
		);
		rewardHistoryRepository.save(rewardHistory);
	}

	// 오늘 출석 리워드 + 포인트 제공
	@Transactional
	public void rewardAboutTodayAttendance(LocalDateTime attendanceDate, Long userId) {

		User user = findUserById(userId);

		user.addPointAndExp(TODAY_ATTENDANCE_REWARD_POINT, TODAY_ATTENDANCE_REWARD_EXP);

		//연속 출석 수 증가
		user.updateAttendanceForOneWeek(attendanceDate);
	}

	public User findUserById(Long userId) {
		return userRepository.findById(userId)
			.orElseThrow(() -> new NotFoundException(ErrorMessage.USER_NOT_FOUND));
	}

}
