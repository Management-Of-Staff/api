package com.example.sidepot.store.presentation;

import com.example.sidepot.global.Path;
import com.example.sidepot.store.app.EmployeeAttendanceService;
import com.example.sidepot.store.dto.AttendanceRequestDto;
import com.example.sidepot.store.dto.AttendanceResponseDto;
import com.example.sidepot.store.dto.EmployeeAttendanceDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = "매장 출석 관련 APIs")
@RequestMapping(Path.REST_BASE_PATH)
public class EmployeeAttendanceController {

    private final EmployeeAttendanceService employeeAttendanceService;

    @PostMapping("/stores/{storeId}/attendance")
    @ApiOperation(value = "[출석관리] 1. 매장 출석 체크", notes = "매장에 출석을 체크하는 API")
    public AttendanceResponseDto checkAttendance(@PathVariable Long storeId, @RequestBody AttendanceRequestDto attendanceRequest) {
        AttendanceResponseDto response = employeeAttendanceService.checkAttendance(storeId, attendanceRequest);
        return response;
    }

    @GetMapping("/stores/{storeId}/attendance")
    @ApiOperation(value = "[출석관리] 2. 출석 직원 조회", notes = "매장에 출석한 직원을 조회하는 API")
    public List<EmployeeAttendanceDto> getCurrentAttendance(@PathVariable Long storeId) {
        return employeeAttendanceService.getCurrentAttendance(storeId);
    }

}
