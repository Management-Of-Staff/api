package com.example.sidepot.command.work.presentation;


import com.example.sidepot.global.Path;
import com.example.sidepot.global.security.LoginMember;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;


@Api(tags = "대타 알림함 관련 APIs")
@RequiredArgsConstructor
@RequestMapping(value = Path.REST_BASE_PATH)
@RestController
public class CoverWorkNoticeRejectController {

    @ApiOperation(value = "[대타 알림함] 대타 거절", notes = "대타 알림에서 거절하는 API")
    @PostMapping
    public void rejectCoverNotice(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                         @ApiIgnore HttpServletRequest httpServletRequest) {



    }
}
