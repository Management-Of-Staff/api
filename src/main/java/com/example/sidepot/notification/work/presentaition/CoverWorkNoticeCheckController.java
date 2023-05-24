package com.example.sidepot.notification.work.presentaition;

import com.example.sidepot.global.Path;
import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.security.LoginMember;

import com.example.sidepot.notification.work.app.CoverNoticeBoxService;
import com.example.sidepot.notification.work.app.CoverNoticeStatusService;
import com.example.sidepot.notification.work.dto.CoverNoticeResDto.*;
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

@Api(tags = "대타 알림함 APIs")
@RequiredArgsConstructor
@RequestMapping(value = Path.REST_BASE_PATH)
@RestController
public class CoverWorkNoticeCheckController {
    private final CoverNoticeBoxService coverNoticeBoxService;
    private final CoverNoticeStatusService coverNoticeStatusService;
    @ApiOperation(value = "[대타 알림함] 대타 알림함 조회", notes = "대타 관련 알림 조회 API")
    @GetMapping(value = "/notice-box/cover")
    public ResponseEntity<ResponseDto> readCoverWorkNotice(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                           @ApiIgnore HttpServletRequest httpServletRequest) {
        //정책 없음
        List<CoverNoticeBoxResDto> staffNoticeList = coverNoticeBoxService.readCoverNoticeSpec(member);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("대타 알림 조회")
                .data(staffNoticeList)
                .build());
    }

    @ApiOperation(value = "[대타 알리함] 대타 알림함 썸네일 조회", notes = "대타 관련 알림 조회 API")
    @GetMapping(value = "/notice-box/cover/thumbnail")
    public ResponseEntity<ResponseDto> readCoverWorkNoticeThumbnail(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                                    @ApiIgnore HttpServletRequest httpServletRequest) {
        CoverNoticeThumbnailResDto coverNoticeThumbnailResDto = coverNoticeBoxService.readCoverNoticeThumbnail(member);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("대타 알림 썸네일 조회")
                .data(coverNoticeThumbnailResDto)
                .build());
    }

    @ApiOperation(value = "[대타 알림함] 알림 읽기", notes = "대타 알림함에 읽음 요청 API")
    @PutMapping(value = "/notice-box/cover/read/{staffNoticeId}")
    public ResponseEntity<ResponseDto> checkCoverWorkNotice(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                            @PathVariable Long staffNoticeId,
                                                            @ApiIgnore HttpServletRequest httpServletRequest){
        coverNoticeStatusService.checkNotice(member, staffNoticeId);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("알림 읽음")
                .data("")
                .build());
    }

    @ApiOperation(value = "[대타 알림함] 알림 단일 삭제처리", notes = "대타 알림함에 삭제 요청 API")
    @PostMapping(value = "/notice-box/cover/delete/{staffNoticeId}")
    public ResponseEntity<ResponseDto> deleteCoverWorkNotice(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                            @PathVariable Long staffNoticeId,
                                                            @ApiIgnore HttpServletRequest httpServletRequest){
        coverNoticeStatusService.hideNotice(staffNoticeId);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("알림 삭제 처리")
                .data("")
                .build());
    }
}
