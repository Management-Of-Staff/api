package com.example.sidepot.attendance.presentation;

import com.example.sidepot.attendance.app.AttendanceService;
import com.example.sidepot.attendance.dto.AttendanceResponseDto;
import com.example.sidepot.global.Path;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(tags = "매장 출석 관련 APIs")
@RequestMapping(Path.REST_BASE_PATH)
public class AttendanceController {
    private final AttendanceService attendanceService;

    @PostMapping("/attendance/{storeId}/{employeeId}")
    @ApiOperation(value = "[출석관리] 매장 출근", notes = "매장에 출석하는 API")
    public ResponseEntity<AttendanceResponseDto> checkIn(@PathVariable("storeId") Long storeId, @PathVariable("employeeId") Long employeeId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(attendanceService.createAttendanceForCheckIn(storeId, employeeId));
    }

    @PutMapping("/attendance/{attendanceId}/checkOut")
    @ApiOperation(value = "[출석관리] 매장 퇴근", notes = "매장에 퇴근하는 API")
    public ResponseEntity<Void> checkOut(@PathVariable Long attendanceId) {
        attendanceService.createAttendanceForCheckOut(attendanceId);
        return ResponseEntity.ok().build();
    }
}
