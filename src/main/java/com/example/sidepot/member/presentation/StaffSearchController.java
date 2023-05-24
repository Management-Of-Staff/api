package com.example.sidepot.member.presentation;

import com.example.sidepot.global.Path;
import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.member.app.StaffService;
import com.example.sidepot.member.dto.MemberReadDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "고용 관련 APIs")
@RequiredArgsConstructor
@RequestMapping(value = Path.REST_BASE_PATH)
@RestController
public class StaffSearchController {

    private final StaffService staffService;

    @ApiOperation(value = "[직원 관리] 직원 검색", notes = "직원관리에서 직원을 핸드폰 번호로 검색하는 API")
    @PostMapping(value = "/stores/staff-search")
    public ResponseEntity<ResponseDto> findStaffByPhoneNum(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                           @RequestBody MemberReadDto.StaffSearchRequestDto staffSearchRequestDto,
                                                           @ApiIgnore HttpServletRequest httpServletRequest){
        MemberReadDto.StaffSearchResponseDto staffSearchResponseDto
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
