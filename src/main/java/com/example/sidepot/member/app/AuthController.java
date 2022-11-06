package com.example.sidepot.member.app;

import com.example.sidepot.global.Path;
import com.example.sidepot.member.dto.AuthDto;
import com.example.sidepot.member.dto.AuthDto.MemberDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @ApiOperation("회원 가입")
    @PostMapping(Path.REST_BASE_PATH + "/auth/register")
    public ResponseEntity<MemberDto> register(@RequestBody MemberDto memberDto) {
        return ResponseEntity.ok(authService.register(memberDto));
    }
}
