package com.example.sidepot.employment.presentation;

import com.example.sidepot.global.Path;
import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.member.app.StaffService;
import com.example.sidepot.member.dto.MemberReadDto.*;
import com.example.sidepot.employment.app.EmploymentService;
import com.example.sidepot.employment.dto.EmploymentUpdateDto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;


@Api(tags = "고용 관련 APIs")
@RequiredArgsConstructor
@RequestMapping(value = Path.REST_BASE_PATH)
@RestController
public class EmploymentCommandController {
    private final EmploymentService employmentService;


    @ApiOperation(value = "[직원 관리] [보류] 직원 삭제", notes = "매장에서 직원 삭제하는 API")
    @PutMapping(value = "/stores/employment/{employmentId}")
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
    @PostMapping(value = "/stores/employment")
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
    @PostMapping(value = "/stores/employment/{employmentId}")
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
}
