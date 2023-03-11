package com.example.sidepot.store.app;


import com.example.sidepot.store.dto.AttendanceRequestDto;
import com.example.sidepot.store.dto.AttendanceResponseDto;
import com.example.sidepot.store.dto.EmployeeAttendanceDto;

import java.util.List;

/**
 * 매장 출석 관련 서비스
 */
public interface EmployeeAttendanceService {

    /**
     * 매장에 출석 체크
     *
     * @param storeId 출석할 매장 Id
     * @param attendanceRequestDto 출석에 사용될 정보(미정)
     * @return AttendanceResponseDto 출석 체크 응답 값(미정)
     */
    AttendanceResponseDto checkAttendance(Long storeId, AttendanceRequestDto attendanceRequestDto);

    /**
     * 출석 체크한 직원 조회
     *
     * @param storeId 출석 조회할 매장 Id
     * @return List<EmployeeAttendanceDto> 출석 된 직원 목록
     */
    List<EmployeeAttendanceDto> getCurrentAttendance(Long storeId);
}
