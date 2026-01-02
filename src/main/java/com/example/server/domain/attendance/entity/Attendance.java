package com.example.server.domain.attendance.entity;

import java.time.LocalDate;

import com.example.server.domain.user.entity.User;
import com.example.server.global.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(
	name = "attendance",
	uniqueConstraints = {
		@UniqueConstraint(
			name = "uk_user_date",
			columnNames = {"user_id", "attendance_date"}
		)
	}
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Attendance extends BaseTimeEntity {

	@Id
	@Column(name = "attendance_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "attendance_date", nullable = false)
	private LocalDate attendanceDate;

	public static Attendance create(User user, LocalDate attendanceDate) {
		return Attendance.builder()
			.user(user)
			.attendanceDate(attendanceDate)
			.build();
	}
}
