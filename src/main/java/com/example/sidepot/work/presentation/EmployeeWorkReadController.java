package com.example.sidepot.work.presentation;

import com.example.sidepot.global.Path;
import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.security.LoginMember;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "고용 관련 APIs")
@RequiredArgsConstructor
@RequestMapping(value = Path.REST_BASE_PATH)
@RestController
public class EmployeeWorkReadController {

    //    @ApiOperation(value = "[직원 관리] 매장 직원 목록 조회", notes = "특정 매장의 직원 목록을 보는 API")
//    @GetMapping(value = "/stores/{storeId}/employments")
//    public ResponseEntity<ResponseDto> readAllStaffByStoreId(@ApiIgnore @AuthenticationPrincipal LoginMember member,
//                                                             @PathVariable("storeId") Long storeId,
//                                                             @ApiIgnore HttpServletRequest httpServletRequest){
//        Map<List<Serializable>, List<StaffWork>> listListMap
//                = workReadService.readAllEmployment(member.getMemberId(), storeId);
//        return ResponseEntity.ok(ResponseDto.builder()
//                .path(httpServletRequest.getServletPath())
//                .statusCode(HttpStatus.OK.value())
//                .method(httpServletRequest.getMethod())
//                .message("매장 직원 목록 조회")
//                .data(listListMap)
//                .build());
//}

    @ApiOperation(value = "[직원 관리] 매장 직원 정보 조회", notes ="특정 매장 직원의 상세 정보를 보는 API")
    @GetMapping(value = "/stores/employments/{employmentId}")
    public ResponseEntity<ResponseDto> readStoreStaffByStaffId(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                               @PathVariable("employmentId") Long employmentId,
                                                               @ApiIgnore HttpServletRequest httpServletRequest){
//        EmploymentReadDto.ReadOneEmploymentResponse readOneEmploymentResponse
//                = employmentService.readEmploymentDetail(member, employmentId);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("매장 직원 조회")
                .data("")
                .build());
    }
}
