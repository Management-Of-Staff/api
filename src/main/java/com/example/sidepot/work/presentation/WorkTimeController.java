package com.example.sidepot.work.presentation;

import com.example.sidepot.global.Path;
import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.work.app.WorkTimeCommandService;
import com.example.sidepot.work.dto.WorkTimeRequest.*;
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

@Api(tags = "매장 직원 관리 APIs")
@RequiredArgsConstructor
@RequestMapping(value = Path.REST_BASE_PATH)
@RestController
public class WorkTimeController {

    private final WorkTimeCommandService workTimeCommandService;

    @ApiOperation(value = "[홈] 일별 근무 조회", notes = "매장 일별 일정을 보여주는 API")
    @GetMapping()
    public ResponseEntity<ResponseDto> read(){
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value =  "[직원 관리] 근무 추가", notes = "특정 직원의 근무를 추가하는 API")
    @PreAuthorize("hasAnyAuthority('OWNER','ADMIN')")
    @PostMapping(value = "/employments/{employmentId}/schedule")
    public ResponseEntity<ResponseDto> updateEmploymentWorkSchedule(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                                    @PathVariable("employmentId") Long employmentId,
                                                                    @RequestBody WeekWorkAddRequest weekWorkAddRequest,
                                                                    @ApiIgnore HttpServletRequest httpServletRequest) {
        workTimeCommandService.createEmploymentWorkSchedule(member, employmentId, weekWorkAddRequest);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("근무 일정 생성")
                .data("")
                .build());
    }

    @ApiOperation(value = "[직원 관리] 근무 삭제", notes = "직원의 근무를 추가하는 API")
    @PreAuthorize("hasAnyAuthority('OWNER','ADMIN')")
    @DeleteMapping(value = "/employments/{employmentId}/schedule")
    public ResponseEntity<ResponseDto> deleteEmploymentWorkSchedule(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                                    @PathVariable("employmentId") Long employmentId,
                                                                    @RequestBody WeekWorkDeleteRequest weekWorkDeleteRequest,
                                                                    @ApiIgnore HttpServletRequest httpServletRequest){
        workTimeCommandService.deleteEmploymentWorkSchedule(member,employmentId ,weekWorkDeleteRequest);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("근무 일정 삭제")
                .data("")
                .build());
    }

    @ApiOperation(value = "[직원 관리] 근무 수정", notes = "직원의 근무를 수정하는 API")
    @PreAuthorize("hasAnyAuthority('OWNER','ADMIN')")
    @PutMapping(value = "/employment/{employmentId}/schedule")
    public ResponseEntity<ResponseDto> updateEmploymentWorkSchedule(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                                    @PathVariable("employmentId") Long employmentId,
                                                                    @RequestBody WeekWorkUpdateRequest weekWorkUpdateRequest,
                                                                    @ApiIgnore HttpServletRequest httpServletRequest){
        workTimeCommandService.updateEmploymentWorkSchedule(member, employmentId, weekWorkUpdateRequest);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("근무 일정 수정")
                .data("")
                .build());
    }
}
