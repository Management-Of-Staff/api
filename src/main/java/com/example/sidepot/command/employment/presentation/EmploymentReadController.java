package com.example.sidepot.command.employment.presentation;

import com.example.sidepot.command.employment.dto.EmploymentListResDto;
import com.example.sidepot.command.employment.app.EmploymentReadService;
import com.example.sidepot.command.employment.dto.EmploymentReadDto.ReadOneEmploymentResponse;
import com.example.sidepot.command.employment.repository.EmploymentDaoRepository;
import com.example.sidepot.global.Path;
import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.security.LoginMember;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@Api(tags = "고용 관련 APIs")
@RequiredArgsConstructor
@RequestMapping(value = Path.REST_BASE_PATH)
@RestController
public class EmploymentReadController {

    private final EmploymentReadService employmentReadService;
    private final EmploymentDaoRepository employmentDaoRepository;
    @ApiOperation(value = "[직원 관리] 매장 직원 목록 조회", notes = "특정 매장 직원 목록을 조회하는 API")
    @GetMapping(value = "/stores/{storeId}/employments")
    public ResponseEntity<ResponseDto> updateStoreStaffRankAndWage(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                                   @PathVariable("storeId") Long storeId,
                                                                   @ApiIgnore HttpServletRequest httpServletRequest) {
        List<EmploymentListResDto> 직원목록조회 = employmentReadService.readEmploymentsByStore(storeId, LocalDate.of(2023, 05, 22));
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getServletPath())
                .message("근무 정보 조회")
                .data(직원목록조회)
                .build());
    }

    @ApiOperation(value = "[직원 관리] 매장 직원 정보 조회", notes ="특정 매장 직원의 상세 정보를 보는 API")
    @GetMapping(value = "/stores/employments/{employmentId}")
    public ResponseEntity<ResponseDto> readStoreStaffByStaffId(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                               @PathVariable("employmentId") Long employmentId,
                                                               @ApiIgnore HttpServletRequest httpServletRequest){
        ReadOneEmploymentResponse employmentResponse = employmentDaoRepository.findById(employmentId).orElseThrow();// #error
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("매장 직원 조회")
                .data(employmentResponse)
                .build());
    }
}
