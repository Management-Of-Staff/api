package com.example.sidepot.work.presentation;

import com.example.sidepot.global.Path;
import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.security.LoginMember;

import com.example.sidepot.work.app.CoverWorkNoticeReadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "대타 관련 APIs")
@RequiredArgsConstructor
@RequestMapping(value = Path.REST_BASE_PATH)
@RestController
public class CoverWorkNoticeReadController {

    private final CoverWorkNoticeReadService coverWorkNoticeReadService;
    @ApiOperation(value = "[대타] 대타 알림함", notes = "대타 관련 알림 보기 API")
    @GetMapping(value = "/cover-works/notice-box")
    public ResponseEntity<ResponseDto> readCoverWorkNotice(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                           @ApiIgnore HttpServletRequest httpServletRequest) {
        coverWorkNoticeReadService.readCoverNoticeOfStaff(member);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("대타 알림 조회")
                .data("")
                .build());
    }

    @ApiOperation(value = "[대타] 대타 알림함 썸네일", notes = "대타 관련 알림 보기 API")
    @GetMapping(value = "/cover-works/notice-box/thumbnail")
    public ResponseEntity<ResponseDto> readCoverWorkNoticeThumbnail(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                                    @ApiIgnore HttpServletRequest httpServletRequest) {
        coverWorkNoticeReadService.readCoverNoticeOfStaff(member);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("대타 알림 썸네일 조회")
                .data("")
                .build());
    }
}
