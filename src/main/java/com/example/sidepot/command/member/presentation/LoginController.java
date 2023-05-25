package com.example.sidepot.command.member.presentation;

import com.example.sidepot.global.Path;
import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.command.member.app.LoginService;
import com.example.sidepot.command.member.dto.AuthDto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "로그인 관련 APIs")
@RequestMapping(value = Path.REST_BASE_PATH + "/member-account")
@RequiredArgsConstructor
@RestController
public class LoginController {

    private final LoginService loginService;

    @ApiOperation(value = "[사장] 로그인", notes = "사장 로그인 API")
    @PostMapping(value = "/sign-in/owner")
    public ResponseEntity<TokenDto> ownerLongin(@RequestBody MemberLoginDto memberLoginDto){
        return ResponseEntity.ok(loginService.OwnerLogin(memberLoginDto));
    }

    @ApiOperation(value = "[직원] 로그인", notes = "직원 로그인 API")
    @PostMapping(value = "/sign-in/staff")
    public ResponseEntity<TokenDto> staffLogin(@RequestBody MemberLoginDto memberLoginDto){
        return ResponseEntity.ok(loginService.staffLogin(memberLoginDto));
    }

    @ApiOperation(value = "로그아웃", notes = "로그아웃 API")
    @GetMapping(value = "/log-out")
    public ResponseEntity<ResponseDto> logOut(@RequestHeader String token){
        return ResponseEntity.ok(loginService.logout(token));
    }

    @ApiOperation(value = "토큰 재발급", notes = "토큰 재발급")
    @GetMapping(value = "/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestHeader String token){
        return ResponseEntity.ok(loginService.reissue(token));
    }
}
