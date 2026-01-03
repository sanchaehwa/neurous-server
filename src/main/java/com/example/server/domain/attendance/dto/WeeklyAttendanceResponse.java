package com.example.server.domain.attendance.dto;

import java.time.LocalDate;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WeeklyAttendanceResponse {
	private boolean monday;
	private boolean tuesday;
	private boolean wednesday;
	private boolean thursday;
	private boolean friday;
	private boolean saturday;
	private boolean sunday;

	public static WeeklyAttendanceResponse of(Set<LocalDate> attendedDates, LocalDate monday) {
		return WeeklyAttendanceResponse.builder()
			.monday(attendedDates.contains(monday))
			.tuesday(attendedDates.contains(monday.plusDays(1)))
			.wednesday(attendedDates.contains(monday.plusDays(2)))
			.thursday(attendedDates.contains(monday.plusDays(3)))
			.friday(attendedDates.contains(monday.plusDays(4)))
			.saturday(attendedDates.contains(monday.plusDays(5)))
			.sunday(attendedDates.contains(monday.plusDays(6)))
			.build();
	}
}
