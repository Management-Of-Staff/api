package com.example.sidepot.command.work.presentation;

import com.example.sidepot.command.work.dto.CoverWorkResDto.RequestedCoverWorkResDto;
import com.example.sidepot.global.Path;
import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.command.work.repository.query.CoverWorkDaoRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "대타 관련 APIs")
@RequiredArgsConstructor
@RequestMapping(value = Path.REST_BASE_PATH)
@RestController
public class CoverWorkReadController {

    private final CoverWorkDaoRepository coverWorkDaoRepository;

    @ApiOperation(value = "[대타] 요청한 대타 목록 보기", notes = "요청한 대타 조회 API")
    //@PreAuthorize("hasAnyAuthority('STAFF','ADMIN')")
    @GetMapping(value = "/cover-works/requests")
    public ResponseEntity<ResponseDto> readRequestedCoverWorkList(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                                  @ApiIgnore HttpServletRequest httpServletRequest) {
        List<RequestedCoverWorkResDto> requestedCoverWorkResDtoList
                = coverWorkDaoRepository.readRequestedCoverWork(member.getMemberId());
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("요청한 대타 목록")
                .data(requestedCoverWorkResDtoList)
                .build());
    }

    @ApiOperation(value = "[대타] 수락한 대타 목록 보기", notes = "수락한 대타 조회 API")
    //@PreAuthorize("hasAnyAuthority('STAFF','ADMIN')")
    @GetMapping(value = "/cover-works/requests/accepted")
    public ResponseEntity<ResponseDto> readAcceptedCoverWorkList(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                                 @ApiIgnore HttpServletRequest httpServletRequest) {


        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("수락한 대타 목록")
                .data("")
                .build());
    }
}
