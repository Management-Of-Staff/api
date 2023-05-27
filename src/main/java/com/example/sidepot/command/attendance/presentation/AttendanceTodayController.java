package com.example.sidepot.command.attendance.presentation;


import com.example.sidepot.command.attendance.dto.AttendanceTodayResDto;
import com.example.sidepot.command.attendance.repository.AttendanceDaoRepository;
import com.example.sidepot.command.attendance.repository.CoverAttendanceDaoRepository;
import com.example.sidepot.global.Path;
import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.security.LoginMember;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
@Api(tags = "근무 출퇴근 관련 APIs")
@RequiredArgsConstructor
@RequestMapping(value = Path.REST_BASE_PATH)
@RestController
public class AttendanceTodayController {

    private final AttendanceDaoRepository attendanceDaoRepository;
    private final CoverAttendanceDaoRepository coverAttendanceDaoRepository;

    @ApiOperation(value = "[직원(출퇴근)] 오늘 근무 정보", notes = "오늘 출근 정보를 보는 API")
    @GetMapping (value = "/attendances/today")
    public ResponseEntity<ResponseDto> createCoverWorkRequest(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                              @RequestParam(value = "today")
                                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate today,
                                                              @ApiIgnore HttpServletRequest httpServletRequest) {

        AttendanceTodayResDto attendanceTodayResDto = attendanceDaoRepository.readAttendanceToday(member.getMemberId(), today);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("오늘 출근 정보")
                .data(attendanceTodayResDto)
                .build());
    }
}
