package com.example.sidepot.work.presentation;

import com.example.sidepot.global.Path;
import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.work.dto.StaffCoverSchedule;
import com.example.sidepot.work.dto.StaffWorkScheduleResDto;
import com.example.sidepot.work.dto.StoreWorkerResDto;
import com.example.sidepot.work.repository.query.CoverWorkDaoRepository;
import com.example.sidepot.work.repository.query.CoverWorkResDto;
import com.example.sidepot.work.repository.query.WorkTimeDaoRepository;
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
import java.time.YearMonth;
import java.util.List;

@Api(tags = "근무 종합 조회 APIs")
@RequiredArgsConstructor
@RequestMapping(value = Path.REST_BASE_PATH)
@RestController
public class WorkQueryController {

    private final WorkTimeDaoRepository workTimeDaoRepository;
    private final CoverWorkDaoRepository coverWorkDaoRepository;

    @ApiOperation(value = "[매장] 오늘 근무 일정표", notes = "특정 날짜의 매장의 근무하는 직원들을 조회하는 API")
    //@PreAuthorize("hasAnyAuthority('OWNER','ADMIN')")
    @GetMapping(value = "/works/store/{storeId}/today")
    public ResponseEntity<ResponseDto> readStoreWorkToday(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                         @PathVariable Long storeId,
                                                         @RequestParam(value = "today")
                                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate today,
                                                         @ApiIgnore HttpServletRequest httpServletRequest) {
        List<StoreWorkerResDto> storeWorkToday = workTimeDaoRepository.getStoreWorkToday(storeId, today);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("오늘 근무 일정표")
                .data(storeWorkToday)
                .build());
    }

    @ApiOperation(value = "[직원] 이번달 근무 일정표, 요청 대타 일정표 ", notes = "직원의 이번달 근무 일정표 API")
    //@PreAuthorize("hasAnyAuthority('STAFF','ADMIN')")
    @GetMapping(value = "/works/staff/year-month")
    public ResponseEntity<ResponseDto> readStoreWorkToday(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                         @RequestParam(value = "yyyy") int year,
                                                         @RequestParam(value = "mm") int month,
                                                         @ApiIgnore HttpServletRequest httpServletRequest) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();
        List<StaffWorkScheduleResDto> staffsWorkByYearMonth
                = workTimeDaoRepository.getStaffsWorkByYearMonth(member.getMemberId(), firstDayOfMonth, lastDayOfMonth);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("이번달 근무 일정표")
                .data(staffsWorkByYearMonth)
                .build());
    }

    @ApiOperation(value = "[직원] 직원의 이번달 추가 근무 일정표", notes = "직원이 수락한 근무 일정표 API")
    //@PreAuthorize("hasAnyAuthority('STAFF','ADMIN')")
    @GetMapping(value = "/cover-works/staff/year-month")
    public ResponseEntity<ResponseDto> readStoreWorkToday1(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                          @RequestParam(value = "yyyy") int year,
                                                          @RequestParam(value = "mm") int month,
                                                          @ApiIgnore HttpServletRequest httpServletRequest) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();
        List<StaffCoverSchedule> staffAcceptedCoverByYearMonth
                = coverWorkDaoRepository.getStaffAcceptedCoverByYearMonth(member.getMemberId(), firstDayOfMonth, lastDayOfMonth);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("이번달 추가 근무 일정표")
                .data(staffAcceptedCoverByYearMonth)
                .build());
    }
}
