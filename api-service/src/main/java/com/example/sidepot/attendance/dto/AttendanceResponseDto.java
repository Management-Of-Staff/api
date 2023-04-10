package com.example.sidepot.attendance.dto;

import com.example.sidepot.attendance.domain.Attendance;

import lombok.Builder;
import lombok.Getter;

/**
 * 출근 응답 DTO
 *
 */
@Getter
public class AttendanceResponseDto {
	private Long attendanceId;

	@Builder
	public AttendanceResponseDto(Long attendanceId) {
		this.attendanceId = attendanceId;
	}

	public static AttendanceResponseDto from(Attendance attendance) {
		return AttendanceResponseDto.builder()
			.attendanceId(attendance.getId())
			.build();
	}
}
