package com.example.sidepot.notification.work.presentaition;

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
import java.util.List;

@Api(tags = "대타 관련 APIs")
@RequiredArgsConstructor
@RequestMapping(value = Path.REST_BASE_PATH)
@RestController
public class CoverWorkNoticeReadController {

    @ApiOperation(value = "[대타] 대타 알림함", notes = "대타 관련 알림 보기 API")
    @GetMapping(value = "/cover-works/notice-box")
    public ResponseEntity<ResponseDto> readCoverWorkNotice(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                           @ApiIgnore HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("대타 알림 조회")
                .data("coverNoticeResDtoList")
                .build());
    }

    @ApiOperation(value = "[대타] 대타 알림함 썸네일", notes = "대타 관련 알림 보기 API")
    @GetMapping(value = "/cover-works/notice-box/thumbnail")
    public ResponseEntity<ResponseDto> readCoverWorkNoticeThumbnail(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                                    @ApiIgnore HttpServletRequest httpServletRequest) {

        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("대타 알림 썸네일 조회")
                .data("coverNoticeThumbnailResDto")
                .build());
    }

    @ApiOperation(value = "[대타] 알림 읽기", notes = "대타 알림함에 읽음 요청 API")
    @GetMapping(value = "/cover-works/notice-box/read/{coverNoticeId}")
    public ResponseEntity<ResponseDto> checkCoverWorkNotice(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                            @PathVariable Long coverNoticeId,
                                                            @ApiIgnore HttpServletRequest httpServletRequest){

        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("알림 읽음")
                .data("")
                .build());
    }
}
