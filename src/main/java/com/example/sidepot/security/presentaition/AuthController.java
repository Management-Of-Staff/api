package com.example.sidepot.security.presentaition;



import com.example.sidepot.global.Path;


import com.example.sidepot.security.app.AuthService;
import com.example.sidepot.security.dto.AuthDto.*;

import io.swagger.annotations.ApiOperation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(Path.REST_BASE_PATH + "/auth")
@RestController
public class AuthController {

    private final String AUTHORIZATION_HEADER = "Authorization";

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @ApiOperation("통합 로그인")
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberLoginDto memberLoginDto) throws Throwable {
        return ResponseEntity.ok().body(authService.login(memberLoginDto));
    }

    @ApiOperation("토큰 재발급")
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestHeader(AUTHORIZATION_HEADER) String bearerToken) throws Throwable {
        return ResponseEntity.ok().body(authService.reissue(bearerToken));
    }

}

