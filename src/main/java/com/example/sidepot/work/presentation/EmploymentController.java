package com.example.sidepot.work.presentation;

import com.example.sidepot.global.Path;
import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.work.app.EmploymentService;
import com.example.sidepot.work.app.WorkTimeReadService;
import com.example.sidepot.work.dao.StaffWork;
import com.example.sidepot.work.dto.EmploymentReadDto.*;
import com.example.sidepot.work.dto.EmploymentUpdateDto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;
import java.util.Map;


@Api(tags = "매장 직원 관리 APIs")
@RequiredArgsConstructor
@RequestMapping(value = Path.REST_BASE_PATH)
@RestController
public class EmploymentController {
    private final EmploymentService employmentService;
    private final WorkTimeReadService workTimeReadService;

    @ApiOperation(value = "[직원 관리] 매장 직원 목록 조회", notes = "특정 매장의 직원 목록을 보는 API")
    @PreAuthorize("hasAnyAuthority('OWNER','ADMIN')")
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
    @PreAuthorize("hasAnyAuthority('OWNER','ADMIN')")
    @GetMapping(value = "/stores/employments/{employmentId]")
    public ResponseEntity<ResponseDto> readStoreStaffByStaffId(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                               @PathVariable("employmentId") Long employmentId,
                                                               @ApiIgnore HttpServletRequest httpServletRequest){

        ReadOneEmploymentResponse readOneEmploymentResponse = employmentService.readOneEmployment(member, employmentId);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("매장 직원 조회")
                .data(readOneEmploymentResponse)
                .build());
    }

    @ApiOperation(value = "[직원 관리] 직원 삭제", notes = "매장에서 직원 삭제하는 API")
    @PreAuthorize("hasAnyAuthority('OWNER','ADMIN')")
    @PutMapping(value = "/stores/employments/{employmentId}")
    public ResponseEntity withdrawStaffAtStore(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                               @PathVariable Long employmentId,
                                               @ApiIgnore HttpServletRequest httpServletRequest){
        employmentService.withdrawStaff(member, employmentId);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("직원 삭제")
                .data("")
                .build());
    }

    @ApiOperation(value = "[직원 관리] 직원 초대", notes = "특정 매장에 특정 직원을 초대하는 API")
    @PreAuthorize("hasAnyAuthority('OWNER','ADMIN')")
    @PostMapping(value = "/stores/employments")
    public ResponseEntity<ResponseDto> addStaffToStoreByStoreId(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                                @RequestParam(value = "storeId", required = true) Long storeId,
                                                                @RequestParam(value = "staffId", required = true) Long staffId,
                                                                @ApiIgnore HttpServletRequest httpServletRequest){
        employmentService.addStaffToStoreByStoreId(storeId, staffId);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("직원 등록")
                .data("")
                .build());
    }

    @ApiOperation(value = "[직원 관리] 매장 직원 정보 수정", notes = "사장님이 매장 직원의 정보를 수정하는 API" )
    @PreAuthorize("hasAnyAuthority('OWNER','ADMIN')")
    @PostMapping(value = "/stores/employments/{employmentId}")
    public ResponseEntity<ResponseDto> updateStoreStaffRankAndWage(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                                   @PathVariable("employmentId") Long employmentId,
                                                                   @RequestBody UpdateRankAndWageRequest updateRankAndWageRequest,
                                                                   @ApiIgnore HttpServletRequest httpServletRequest){
        employmentService.updateStoreStaffRankAndWage(member, employmentId, updateRankAndWageRequest);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .message(httpServletRequest.getServletPath())
                .method("근무 정보 수정")
                .data("")
                .build());
    }
}
