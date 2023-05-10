package com.example.sidepot.work.presentation;

import com.example.sidepot.global.Path;
import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.work.app.WorkReadService;
import com.example.sidepot.work.dao.ReadStoreWorkerOnDay;
import com.example.sidepot.work.domain.WorkTime;
import com.example.sidepot.work.domain.WorkTimeRepository;
import com.example.sidepot.work.dto.ReadWorkResponseDto;
import com.example.sidepot.work.dto.WorkRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Api(tags = "근무 조회 APIs")
@RequiredArgsConstructor
@RequestMapping(value = Path.REST_BASE_PATH)
@RestController
public class WorkReadController {

    private final WorkReadService workReadService;

    @ApiOperation(value = "[매장 홈] 일일 근무 시간 조회", notes = "매장의 특정 요일에 근무하는 직원을 조회하는 API")
    @GetMapping("/stores/{storeId}/work-schedule")
    public ResponseEntity<ResponseDto> readStoreWorkScheduleOnDay(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                                  @ApiIgnore HttpServletRequest httpServletRequest,
                                                                  @PathVariable Long storeId,
                                                                  @DateTimeFormat(pattern = "yyyy.MM.dd")
                                                                  @RequestParam("onDay") LocalDate onDay){
        List<ReadStoreWorkerOnDay.ReadWork> readWorkerList = workReadService.readAllEmploymentOnDay(member, storeId, onDay);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("일일 매장 근무 일정 조회")
                .data(readWorkerList)
                .build());
    }

    @ApiOperation(value = "[직원 홈] 월간 근무 조회", notes = "직원의 월간 근무 조회 API")
    @GetMapping("/staffs/work-schedule")
    public ResponseEntity<ResponseDto> readStaffWorkSchedule(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                             @ApiIgnore HttpServletRequest httpServletRequest,
                                                             @RequestParam("year") int year,
                                                             @RequestParam("month") int month
                                                             ){
        List<WorkReadService.WorkWithCoveredResponseDto> workWithCoveredResponseDtoList = workReadService.readAllWorkOfStaff(member.getMemberId(), year, month);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("월간 직원 근뭄 조회")
                .data(workWithCoveredResponseDtoList)
                .build());
    }

    @ApiOperation(value = "[대타] 직원 모든 고정 근무 조회", notes = "대타 요청을 위한 직원의 모든 근무 조회 API")
    @PreAuthorize("hasAnyAuthority('STAFF','ADMIN')")
    @GetMapping(value = "/works")
    public ResponseEntity<ResponseDto> readWorkSchedule(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                        @ApiIgnore HttpServletRequest httpServletRequest) {

        Map<List<String>, List<ReadWorkResponseDto.ReadWorkByStoreResDto>> listListMap = workReadService.readAllWorkByStore(member.getMemberId());
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("고정 근무 조회")
                .data(listListMap)
                .build());
    }
}
