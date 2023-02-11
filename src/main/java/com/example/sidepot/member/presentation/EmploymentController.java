package com.example.sidepot.member.presentation;

import com.example.sidepot.global.Path;
import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.member.app.EmploymentService;
import com.example.sidepot.member.domain.Auth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;


@Api(tags = "매장 관련 APIs")
@RequiredArgsConstructor
@RequestMapping(value = Path.REST_BASE_PATH + "/employment")
@RestController
public class EmploymentController {

    private final EmploymentService employmentService;

    @ApiOperation(value = "[매장 직원 관리] 1.매장 직원 목록", notes = "특정 매장의 직원 목록을 보는 API")
    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping(value = "/read")
    public ResponseEntity<ResponseDto> readAllStaffByStoreId(@ApiIgnore @AuthenticationPrincipal Auth auth,
                                                             @RequestParam(value = "storeId", required = true) Long storeId){
        return ResponseEntity.ok(employmentService.readAllStaffByStoreId(auth, storeId));
    }

    @ApiOperation(value = "[회원 관리] 2.사장님이 매장 직원 정보 조회", notes ="특정 매장 직원의 상세 정보를 보는 API")
    @PreAuthorize("hasAuthority('OWNER')")
    @GetMapping(value = "/read/{staffId}")
    public ResponseEntity<ResponseDto> readStoreStaffByStaffId(@ApiIgnore @AuthenticationPrincipal Auth auth,
                                                               @RequestParam(value = "storeId", required = true) Long storeId,
                                                               @RequestParam(value = "staffId", required = true) Long staffId){
        return ResponseEntity.ok(employmentService.readEmployment(auth, storeId, staffId));
    }

    @ApiOperation(value = "[회원 관리] 3.직원 초대", notes = "특정 매장에 특정 직원을 초대하는 API")
    @PreAuthorize("hasAuthority('OWNER')")
    @PostMapping(value = "/add")
    public ResponseEntity<ResponseDto> addStaffToStoreByStoreId(@ApiIgnore @AuthenticationPrincipal Auth auth,
                                                                @RequestParam(value = "storeId", required = true) Long storeId,
                                                                @RequestParam(value = "staffId", required = true) Long staffId){
        return ResponseEntity.ok(employmentService.addStaffToStoreByStoreId(storeId, staffId));
    }

//    @ApiOperation(value = "[회원 관리] 4.매장 직원 정보 수정", notes = "사장님이 매장 직원의 정보를 수정하는 API" )
//    @PreAuthorize("hasAuthority('OWNER')")
//    @PostMapping(value = "/store/{storeId}/update-staff/{staffId}")
//    public ResponseEntity<ResponseDto> updateStoreStaffByStaffId(@ApiIgnore @AuthenticationPrincipal Auth auth,
//                                                                 @PathVariable Long storeId,
//                                                                 @PathVariable Long staffId){
//        return ResponseEntity.ok().build();
//    }

//    @ApiOperation(value = "[회원 관리] 5.근로계약서 등록", notes = "근로 계약서를 서버에 저장하는 API")
//    @PreAuthorize("hasAuthority('OWNER')")
//    @PostMapping(value = "/store/{storeId/employment-contract/{staffId}")
//    public ResponseEntity<ResponseDto> createEmploymentContract(@ApiIgnore @AuthenticationPrincipal Auth auth,
//                                                                @PathVariable Long storeId, @PathVariable Long staffId,
//                                                                @RequestPart("contract-file") List<MultipartFile> contractFile,
//                                                                @RequestPart("contract-dto") ContractCreateRequestDto contractCreateRequestDto ){
//        return ResponseEntity.ok().build();
//    }

    @PreAuthorize("hasAuthority('OWNER')")
    @PostMapping(value = "/employment/update-schedule")
    public ResponseEntity<ResponseDto> updateEmploymentWorkSchedule(@ApiIgnore @AuthenticationPrincipal Auth auth,
                                                                    @RequestParam(value = "storeId", required = true) Long storeId,
                                                                    @RequestParam(value = "staffId", required = true) Long staffId,
                                                                    HttpServletRequest request){

        return ResponseEntity.ok(employmentService.updateEmploymentWorkSchedule(auth, storeId, staffId)
                .builder().path(request.getServletPath()).build());
    }
}
