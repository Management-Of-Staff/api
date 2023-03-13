package com.example.sidepot.member.presentation;

import com.example.sidepot.global.Path;
import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.member.app.EmploymentService;
import com.example.sidepot.member.domain.Member;
import com.example.sidepot.member.dto.EmploymentAddDto.*;
import com.example.sidepot.member.dto.EmploymentUpdateDto.*;
import com.example.sidepot.member.dto.WorkTimeRequest.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


@Api(tags = "매장 관련 APIs")
@RequiredArgsConstructor
@RequestMapping(value = Path.REST_BASE_PATH + "/employment")
@RestController
public class EmploymentController {

    private final EmploymentService employmentService;

    @ApiOperation(value = "[매장 직원 관리] 1.매장 직원 목록", notes = "특정 매장의 직원 목록을 보는 API")
    @PreAuthorize("hasAnyAuthority('OWNER','ADMIN')")
    @GetMapping(value = "/read-all")
    public ResponseEntity<ResponseDto> readAllStaffByStoreId(@ApiIgnore @AuthenticationPrincipal Member auth,
                                                             @RequestParam(value = "storeId", required = true) Long storeId){
        return ResponseEntity.ok(employmentService.readAllStaffByStoreId(auth, storeId));
    }

    @ApiOperation(value = "[매장 직원 관리] 2.사장님이 매장 직원 정보 조회", notes ="특정 매장 직원의 상세 정보를 보는 API")
    @PreAuthorize("hasAnyAuthority('OWNER','ADMIN')")
    @GetMapping(value = "/read")
    public ResponseEntity<ResponseDto> readStoreStaffByStaffId(@ApiIgnore @AuthenticationPrincipal Member auth,
                                                               @RequestParam(value = "storeId", required = true) Long storeId,
                                                               @RequestParam(value = "staffId", required = true) Long staffId){
        return ResponseEntity.ok(employmentService.readEmployment(auth, storeId, staffId));
    }

    @ApiOperation(value = "[매장 직원 관리] 3.직원 초대", notes = "특정 매장에 특정 직원을 초대하는 API")
    @PreAuthorize("hasAnyAuthority('OWNER','ADMIN')")
    @PostMapping(value = "/add")
    public ResponseEntity<ResponseDto> addStaffToStoreByStoreId(@ApiIgnore @AuthenticationPrincipal Member auth,
                                                                @RequestParam(value = "storeId", required = true) Long storeId,
                                                                @RequestParam(value = "staffId", required = true) Long staffId){
        return ResponseEntity.ok(employmentService.addStaffToStoreByStoreId(storeId, staffId));
    }

    @ApiOperation(value = "[매장 직원 관리] 4. 직원 검색", notes = "초대할 직원을 핸드폰 번호로 검색하는 API")
    @PreAuthorize("hasAnyAuthority('OWNER','ADMIN')")
    @PostMapping("/find-invitee")
    public ResponseEntity<ResponseDto> findStaffToInvite(@ApiIgnore @AuthenticationPrincipal Member auth,
                                                         @RequestBody FindStaffToInviteRequest findStaffToInviteRequest){
        return ResponseEntity.ok(employmentService.findStaffToInvite(auth, findStaffToInviteRequest));
    }

    @ApiOperation(value = "[매장 직원 관리] 5.매장 직원 정보 수정", notes = "사장님이 매장 직원의 정보를 수정하는 API" )
    @PreAuthorize("hasAnyAuthority('OWNER','ADMIN')")
    @PostMapping(value = "/update")
    public ResponseEntity<ResponseDto> updateStoreStaffRankAndWage(@ApiIgnore @AuthenticationPrincipal Member auth,
                                                                   @RequestBody UpdateRankAndWageRequest updateRankAndWageRequest){
        return ResponseEntity.ok(employmentService.updateStoreStaffRankAndWage(auth, updateRankAndWageRequest));
    }



    @ApiOperation(value =  "[매장 직원 관리] 6.근무 추가", notes = "특정 직원의 근무를 추가하는 API")
    @PreAuthorize("hasAnyAuthority('OWNER','ADMIN')")
    @PostMapping(value = "/update-schedule")
    public ResponseEntity<ResponseDto> updateEmploymentWorkSchedule(@ApiIgnore @AuthenticationPrincipal Member auth,
                                                                    @RequestParam(value = "storeId", required = true) Long storeId,
                                                                    @RequestParam(value = "staffId", required = true) Long staffId,
                                                                    @RequestBody WeekWorkAddRequest weekWorkAddRequest) {

        return ResponseEntity.ok(employmentService.updateEmploymentWorkSchedule(auth, storeId, staffId, weekWorkAddRequest));
    }

    @ApiOperation(value = "[매장 직원 관리] 7. 근무 삭제", notes = "직원의 근무를 추가하는 API")
    @PreAuthorize("hasAnyAuthority('OWNER','ADMIN')")
    @PutMapping(value = "/update-schedule")
    public ResponseEntity<ResponseDto> deleteEmploymentWorkSchedule(@ApiIgnore @AuthenticationPrincipal Member auth,
                                                                    @RequestBody WeekWorkDeleteRequest weekWorkDeleteRequest){
        return ResponseEntity.ok(employmentService.deleteEmploymentWorkSchedule(auth, weekWorkDeleteRequest));
    }
}
