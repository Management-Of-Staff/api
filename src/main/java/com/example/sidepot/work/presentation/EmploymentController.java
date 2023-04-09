package com.example.sidepot.work.presentation;

import com.example.sidepot.global.Path;
import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.member.app.StaffService;
import com.example.sidepot.member.dto.MemberReadDto.*;
import com.example.sidepot.work.app.EmploymentService;
import com.example.sidepot.work.app.WorkTimeReadService;
import com.example.sidepot.work.dao.StaffWork;
import com.example.sidepot.work.dao.StaffWorkOnDay;
import com.example.sidepot.work.dto.EmploymentReadDto.*;
import com.example.sidepot.work.dto.EmploymentUpdateDto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Api(tags = "직원 관리 APIs")
@RequiredArgsConstructor
@RequestMapping(value = Path.REST_BASE_PATH)
@RestController
public class EmploymentController {
    private final EmploymentService employmentService;
    private final WorkTimeReadService workTimeReadService;
    private final StaffService staffService;

    @ApiOperation(value = "[홈] 일별 근무 조회", notes = "매장 일별 일정을 보여주는 API")
    @GetMapping("/home/stores/employments")
    public ResponseEntity<ResponseDto> readEmploymentOnDay(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                           @RequestParam("store_Id") Long storeId,
                                                           @RequestParam("on_day") String onDay,
                                                           @ApiIgnore HttpServletRequest httpServletRequest){
        List<StaffWorkOnDay> staffWorkOnDays
                = workTimeReadService.readAllEmploymentOnDay(member, storeId, LocalDate.parse(onDay));
        return ResponseEntity.ok(ResponseDto.builder()
                .statusCode(HttpStatus.OK.value())
                .method(HttpMethod.GET.name())
                .message("홈 근무 조회")
                .data(staffWorkOnDays)
                .build());
    }

    @ApiOperation(value = "[직원 관리] 매장 직원 목록 조회", notes = "특정 매장의 직원 목록을 보는 API")
    @GetMapping(value = "/stores/{storeId}/employments")
    public ResponseEntity<ResponseDto> readAllStaffByStoreId(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                             @PathVariable("storeId") Long storeId,
                                                             @ApiIgnore HttpServletRequest httpServletRequest){
        Map<List<Serializable>, List<StaffWork>> listListMap
                = workTimeReadService.readAllEmployment(member.getMemberId(), storeId);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("매장 직원 목록 조회")
                .data(listListMap)
                .build());
    }

    @ApiOperation(value = "[직원 관리] 매장 직원 정보 조회", notes ="특정 매장 직원의 상세 정보를 보는 API")
    @GetMapping(value = "/stores/employments/{employmentId}")
    public ResponseEntity<ResponseDto> readStoreStaffByStaffId(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                               @PathVariable("employmentId") Long employmentId,
                                                               @ApiIgnore HttpServletRequest httpServletRequest){
        ReadOneEmploymentResponse readOneEmploymentResponse
                = employmentService.readEmploymentDetail(member, employmentId);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("매장 직원 조회")
                .data(readOneEmploymentResponse)
                .build());
    }

    @ApiOperation(value = "[직원 관리] [보류] 직원 삭제", notes = "매장에서 직원 삭제하는 API")
    @PutMapping(value = "/stores/employments/{employmentId}")
    public ResponseEntity withdrawStaffAtStore(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                               @PathVariable Long employmentId,
                                               @ApiIgnore HttpServletRequest httpServletRequest){
        employmentService.withdrawEmployment(member, employmentId);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("직원 삭제")
                .data("")
                .build());
    }

    @ApiOperation(value = "[직원 관리] 직원 초대", notes = "특정 매장에 특정 직원을 초대하는 API")
    @PostMapping(value = "/stores/employments")
    public ResponseEntity<ResponseDto> addStaffToStoreByStoreId(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                                @RequestParam(value = "storeId", required = true) Long storeId,
                                                                @RequestParam(value = "staffId", required = true) Long staffId,
                                                                @ApiIgnore HttpServletRequest httpServletRequest){
        employmentService.createEmployment(storeId, staffId);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("직원 등록")
                .data("")
                .build());
    }

    @ApiOperation(value = "[직원 관리] 매장 직원 정보 수정", notes = "사장님이 매장 직원의 정보를 수정하는 API" )
    @PostMapping(value = "/stores/employments/{employmentId}")
    public ResponseEntity<ResponseDto> updateStoreStaffRankAndWage(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                                   @PathVariable("employmentId") Long employmentId,
                                                                   @RequestBody UpdateRankAndWageRequest updateRankAndWageRequest,
                                                                   @ApiIgnore HttpServletRequest httpServletRequest){
        employmentService.updateEmploymentRankAndWage(member, employmentId, updateRankAndWageRequest);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getServletPath())
                .message("근무 정보 수정")
                .data("")
                .build());
    }

    @ApiOperation(value = "[직원 관리] 직원 검색", notes = "직원관리에서 직원을 핸드폰 번호로 검색하는 API")
    @PostMapping(value = "/stores/staff-search")
    public ResponseEntity<ResponseDto> findStaffByPhoneNum(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                           @RequestBody StaffSearchRequestDto staffSearchRequestDto,
                                                           @ApiIgnore HttpServletRequest httpServletRequest){
        StaffSearchResponseDto staffSearchResponseDto
                = staffService.findStaffByPhoneNum(member, staffSearchRequestDto);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getServletPath())
                .message("직원 검색")
                .data(staffSearchResponseDto)
                .build());
    }


}
