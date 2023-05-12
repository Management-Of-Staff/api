package com.example.sidepot.work.presentation;

import com.example.sidepot.global.Path;
import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.work.app.CoverWorkCommandService;
import com.example.sidepot.work.app.CoverWorkNoticeCommandService;
import com.example.sidepot.work.dto.CoverWorkRequestDto.*;
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

@Api(tags = "대타 명령 APIs")
@RequiredArgsConstructor
@RequestMapping(value = Path.REST_BASE_PATH)
@RestController
public class CoverWorkCommandController {

    private final CoverWorkCommandService coverWorkCommandService;
    private final CoverWorkNoticeCommandService coverWorkNoticeCommandService;

    @ApiOperation(value = "[대타] 대타 요청", notes = "대타 요청을 생성하는 API")
    @PostMapping(value = "/cover-works/requests")
    public ResponseEntity<ResponseDto> createCoverWorkRequest(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                              @RequestBody List<CreateCoverWorkReqDto> createCoverWorkReqDtoList,
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

    @ApiOperation(value = "[대타] 대타 수락", notes = "요청된 대타를 수락하는 API")
    @PostMapping(value = "/cover-works/requests/accept")
    public ResponseEntity<ResponseDto> acceptCoverWorkRequest(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                              @RequestBody List<AcceptCoverWorkReqDto> acceptCoverWorkReqDtoList,
                                                              @ApiIgnore HttpServletRequest httpServletRequest) {
        coverWorkCommandService.acceptCoverWorkRequest(member, acceptCoverWorkReqDtoList);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("대타 요청 수락")
                .data("")
                .build());
    }

    @ApiOperation(value = "[대타] 수락한 대타 취소 ", notes = "수락한 대타를 취소하는 API")
    @PostMapping(value = "/cover-works/requests/accept/cancel")
    public ResponseEntity<ResponseDto> acceptedCoverWorkCancel(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                               @ApiIgnore HttpServletRequest httpServletRequest) {
        //정책 없음
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("수락한 대타 취소")
                .data("")
                .build());
    }

    @ApiOperation(value = "[대타] 요청한 대타 취소", notes = "요청한 대타 취소하는 API")
    @PostMapping(value = "/cover-works/requests/cancel")
    public ResponseEntity<ResponseDto> requestedCoverWorkCancel(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                               @ApiIgnore HttpServletRequest httpServletRequest) {
        //정책 없음
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("요청한 대타 취소")
                .data("")
                .build());
    }

}
