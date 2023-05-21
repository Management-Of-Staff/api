package com.example.sidepot.work.presentation;

import com.example.sidepot.global.Path;
import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.notification.work.domain.RejectMessage;
import com.example.sidepot.work.app.CoverAcceptService;
import com.example.sidepot.work.app.CoverCancelService;
import com.example.sidepot.work.app.CoverRequestService;
import com.example.sidepot.work.dto.CoverWorkRequestDto.CreateCoverWorkReqDto;
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

@Api(tags = "대타 APIs")
@RequiredArgsConstructor
@RequestMapping(value = Path.REST_BASE_PATH)
@RestController
public class CoverWorkCommandController {

    private final CoverRequestService coverRequestService;
    private final CoverAcceptService coverAcceptService;
    private final CoverCancelService coverCancelService;

    @ApiOperation(value = "[대타] 대타 요청", notes = "대타 요청을 생성하는 API")
    @PostMapping(value = "/cover-works/requests")
    public ResponseEntity<ResponseDto> createCoverWorkRequest(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                              @RequestBody List<CreateCoverWorkReqDto> createCoverWorkReqDtoList,
                                                              @ApiIgnore HttpServletRequest httpServletRequest) {

        coverRequestService.requestCoverWork(member, createCoverWorkReqDtoList);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("대타 요청 생성")
                .data("")
                .build());
    }

    @ApiOperation(value = "[대타] 대타 수락", notes = "요청된 대타를 수락하는 API")
    @PostMapping(value = "/cover-works/requests/accept/{coverManagerId}")
    public ResponseEntity<ResponseDto> acceptCoverWorkRequest(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                              @ApiIgnore HttpServletRequest httpServletRequest,
                                                              @PathVariable Long coverManagerId) {
        coverAcceptService.acceptCoverWork(member, coverManagerId);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("대타 요청 수락")
                .data("")
                .build());
    }

    @ApiOperation(value = "[대타] 수락한 대타 취소 ", notes = "수락한 대타를 취소하는 API")
    @PostMapping(value = "/cover-works/requests/accept/{coverManagerId}/cancel")
    public ResponseEntity<ResponseDto> acceptedCoverWorkCancel(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                               @ApiIgnore HttpServletRequest httpServletRequest,
                                                               @PathVariable Long coverManagerId,
                                                               @RequestParam(value = "reason") String messageType) {
        coverCancelService.cancelAcceptedCover(member, coverManagerId, RejectMessage.valueOf(messageType));
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("수락한 대타 취소")
                .data("")
                .build());
    }

    @ApiOperation(value = "[대타] 요청한 대타 취소", notes = "요청한 대타 취소하는 API")
    @PostMapping(value = "/cover-works/requests/{coverManagerId}/cancel")
    public ResponseEntity<ResponseDto> requestedCoverWorkCancel(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                                @ApiIgnore HttpServletRequest httpServletRequest,
                                                                @PathVariable Long coverManagerId) {
        coverCancelService.cancelRequestedCover(member, coverManagerId);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("요청한 대타 취소")
                .data("")
                .build());
    }

}
