package com.example.sidepot.command.employment.presentation;

import com.example.sidepot.command.employment.app.EmploymentReadService;
import com.example.sidepot.global.Path;
import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.security.LoginMember;
import io.swagger.annotations.Api;
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
    @GetMapping(value = "/stores/{storeId}/employments")
    public ResponseEntity<ResponseDto> updateStoreStaffRankAndWage(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                                   @PathVariable("storeId") Long storeId,
                                                                   @ApiIgnore HttpServletRequest httpServletRequest) {
        List<EmploymentReadService.EmploymentListResDto> 직원목록조회 = employmentReadService.직원목록조회(storeId, LocalDate.of(2023, 05, 22));
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getServletPath())
                .message("근무 정보 수정")
                .data(직원목록조회)
                .build());
    }
}
