package com.example.sidepot.attendance.app;


import com.example.sidepot.attendance.dto.AttendanceResponseDto;
import com.example.sidepot.attendance.dto.EmployeeAttendanceDto;

import java.util.List;

/**
 * 매장 출석 관련 서비스
 */
public interface AttendanceService {

    /**
     * 출석
     *
     * @param storeId 매장 ID
     * @param employeeId 직원 ID
     * @return AttendanceResponseDto 출석 체크 응답 값
     */
    AttendanceResponseDto createAttendanceForCheckIn(Long storeId, Long employeeId);

    /**
     * 출석 상태 퇴근으로 변경
     *
     * @param storeId 매장 ID
     * @param employeeId 직원 ID
     */
    void updateAttendanceForCheckOut(Long attendanceId);

    /**
     * 출석 체크한 직원 조회
     *
     * @param storeId 출석 조회할 매장 Id
     * @return List<EmployeeAttendanceDto> 출석 된 직원 목록
     */
    List<EmployeeAttendanceDto> getCurrentAttendanceList(Long storeId);
}
