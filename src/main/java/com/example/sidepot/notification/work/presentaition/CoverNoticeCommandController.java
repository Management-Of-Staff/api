package com.example.sidepot.notification.work.presentaition;

import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.security.LoginMember;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

public class CoverNoticeCommandController {

    public ResponseEntity<ResponseDto> withdrawalMyNotice(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                          @ApiIgnore HttpServletRequest httpServletRequest){

        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("대타 알림 조회")
                .data("")
                .build());
    }

    public ResponseEntity<ResponseDto> withdrawalMyAllNotice(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                             @ApiIgnore HttpServletRequest httpServletRequest){

        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("대타 알림 조회")
                .data("")
                .build());
    }
}
