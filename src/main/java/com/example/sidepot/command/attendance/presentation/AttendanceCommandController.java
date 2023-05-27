package com.example.sidepot.command.attendance.presentation;

import com.example.sidepot.command.attendance.app.CheckInService;
import com.example.sidepot.command.attendance.app.CheckOutService;
import com.example.sidepot.command.attendance.dto.AttendanceTodayResDto;
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
import java.time.LocalDateTime;


@Api(tags = "근무 출퇴근 관련 APIs")
@RequiredArgsConstructor
@RequestMapping(value = Path.REST_BASE_PATH)
@RestController
public class AttendanceCommandController {

    private final CheckInService checkInService;
    private final CheckOutService checkOutService;

    @ApiOperation(value = "[직원(출퇴근)] 근무 출근하기 ", notes = "오늘 출근 하는 API")
    @PostMapping(value = "/attendances/check-in/today")
    public ResponseEntity<ResponseDto> createWorkRequest(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                              @RequestParam(value = "today")
                                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime today,
                                                              @ApiIgnore HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("근무 출근하기")
                .data("")
                .build());
    }

    @ApiOperation(value = "[직원(출퇴근)] 대타 출근하기 ", notes = "오늘 대타 출근 하는 API")
    @PostMapping(value = "/attendances/check-in/cover-today")
    public ResponseEntity<ResponseDto> createCoverWorkRequest(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                              @ApiIgnore HttpServletRequest httpServletRequest) {


        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("대타 출근하기")
                .data("")
                .build());
    }
}
