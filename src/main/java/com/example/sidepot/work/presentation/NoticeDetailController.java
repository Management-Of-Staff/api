package com.example.sidepot.work.presentation;

import com.example.sidepot.global.Path;
import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.work.repository.query.CoverWorkDaoRepository;
import com.example.sidepot.work.repository.query.CoverWorkResDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(tags = "대타 알림함 관련 APIs")
@RequiredArgsConstructor
@RequestMapping(value = Path.REST_BASE_PATH)
@RestController
public class NoticeDetailController {

    private final CoverWorkDaoRepository coverWorkDaoRepository;

    @ApiOperation(value = "[대타 알림함] 알림함 디테일 ", notes = "알림 디테일을 보는 API")
    //@PreAuthorize("hasAnyAuthority('STAFF','ADMIN')")
    @GetMapping(value = "/cover-works/notice-box/{coverManagerId}")
    public ResponseEntity<ResponseDto> readCoverWorkByOfNoticeBox(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                                  @PathVariable Long coverManagerId,
                                                                  @ApiIgnore HttpServletRequest httpServletRequest) {

        List<CoverWorkResDto.CoverWorkByNoticeResDto> coverDetailsOfNoticeBox = coverWorkDaoRepository.getCoverDetailsOfNoticeBox(coverManagerId);
        return ResponseEntity.ok(ResponseDto.builder()
                .path(httpServletRequest.getServletPath())
                .statusCode(HttpStatus.OK.value())
                .method(httpServletRequest.getMethod())
                .message("알림 디테일")
                .data(coverDetailsOfNoticeBox)
                .build());
    }
}
