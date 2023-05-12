package com.example.sidepot.work.presentation;

import com.example.sidepot.global.Path;
import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.work.dao.CoverWorkNotificationResDto;
import com.example.sidepot.work.dao.CoverWorkReadQuery;
import com.example.sidepot.work.dto.CoverWorkRequestDto;
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
public class CoverWorkReadController {

    private final CoverWorkReadQuery coverWorkReadQuery;

    @ApiOperation(value = "[대타] 요청 대타 조회", notes = "요청한 대타 목록 조회 API")
    @GetMapping(value = "/cover-works/requests")
    public ResponseEntity<ResponseDto> readMyCoverWorkRequested(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                                @ApiIgnore HttpServletRequest httpServletRequest) {
        List<CoverWorkNotificationResDto> coverWorkNotificationResList
                = coverWorkReadQuery.readMyCoverWorkRequested(member.getMemberId());
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("요청 대타 조회")
                .data(coverWorkNotificationResList)
                .build());
    }

    @ApiOperation(value = "[대타] 수락 대타 조회", notes = "수락한 대타 목록 조회 API")
    @GetMapping(value = "/cover-works/requests/accept")
    public ResponseEntity<ResponseDto> createCoverWorkRequest(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                              @ApiIgnore HttpServletRequest httpServletRequest) {
        List<CoverWorkNotificationResDto> coverWorkNotificationResList
                = coverWorkReadQuery.readMyCoverWorkAccepted(member.getMemberId());
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("수락 대타 조회")
                .data(coverWorkNotificationResList)
                .build());
    }
}
