package com.example.sidepot.member.presentation;

import com.example.sidepot.global.Path;
import com.example.sidepot.member.app.OwnerService;
import com.example.sidepot.member.dto.MemberDto.OwnerDto;


import com.example.sidepot.security.domain.Auth;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.security.Principal;


@Slf4j
@AllArgsConstructor
@RestController
public class OwnerController {

    private final OwnerService ownerService;

    @ApiOperation("사장 회원 가입")
    @PostMapping(Path.REST_BASE_PATH + "/owner/register")
    public ResponseEntity<?> register(@RequestBody OwnerDto ownerDto) {
        return ResponseEntity.ok().body(ownerService.register(ownerDto));
    }

    @ApiOperation("사장 정보 조회")
    @GetMapping(Path.REST_BASE_PATH + "/owner/read1")
    public ResponseEntity<?> read(@ApiIgnore
                                      @AuthenticationPrincipal Auth auth,
                                      @AuthenticationPrincipal Principal principal,
                                      @AuthenticationPrincipal Authentication authentication) {
        Object dc  = SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        return ResponseEntity.ok().body(auth);
    }
}
