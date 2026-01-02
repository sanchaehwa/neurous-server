package com.example.server.domain.attendance.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.server.domain.attendance.entity.Attendance;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

	//오늘 출석 여부 확인
	boolean existsByUserIdAndAttendanceDate(Long userId, LocalDate attendanceDate);

	//일주일 출석 데이터
	List<Attendance> findAllByUserIdAndAttendanceDateBetween(
		Long userId, LocalDate startDate, LocalDate endDate
	);
}
