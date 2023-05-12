package com.example.sidepot.work;

import com.example.sidepot.global.Path;
import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.work.app.CoverWorkNoticeCommandService;
import com.example.sidepot.work.dto.CoverWorkRequestDto;
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
import java.util.List;

@Api(tags = "임시 테스트 APIs")
@RequiredArgsConstructor
@RequestMapping(value = Path.REST_BASE_PATH)
@RestController
public class TestController {

    private final CoverWorkNoticeCommandService coverWorkNoticeCommandService;

    @ApiOperation(value = "대타 알림 요청 확인")
    @PostMapping(value = "/test")
    public ResponseEntity<ResponseDto> test(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                              @RequestBody List<CoverWorkRequestDto.CreateCoverWorkReqDto> createCoverWorkReqDtoList,
                                                              @ApiIgnore HttpServletRequest httpServletRequest) {
        coverWorkNoticeCommandService.createCoverWorkRequestNotice(member, createCoverWorkReqDtoList);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("대타 요청 생성")
                .data("")
                .build());
    }
}
