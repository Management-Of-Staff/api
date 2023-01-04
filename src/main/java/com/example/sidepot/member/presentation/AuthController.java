package com.example.sidepot.member.presentation;


import com.example.sidepot.global.Path;
import com.example.sidepot.member.app.AuthService;
import com.example.sidepot.security.dto.AuthDto.MemberLoginDto;
import com.example.sidepot.security.dto.AuthDto.TokenDto;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "매장 관련 APIs")
@RequiredArgsConstructor
@RequestMapping(Path.REST_BASE_PATH + "/auth")
@RestController
public class AuthController {

    private final String AUTHORIZATION_HEADER = "Authorization";

    private final AuthService authService;

    @ApiOperation(value = "로그인", notes = "오너, 직원 로그인")
    @ApiResponses({@ApiResponse(code = 200, message = "로그인 완료")})
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberLoginDto memberLoginDto) throws Throwable {
        return ResponseEntity.ok().body(authService.login(memberLoginDto));
    }


    @ApiOperation(value = "토큰 재발급", notes = "리프레시 토큰 재발급")
    @ApiImplicitParam(name = "refreshToken" , value = "리프레시 토큰", required = true,
                      dataType = "String", paramType = "header")
    @ApiResponses({@ApiResponse(code = 200, message = "토큰 재발급 성공")})
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@ApiIgnore @RequestHeader(AUTHORIZATION_HEADER) String bearerToken) throws Throwable {
        return ResponseEntity.ok().body(authService.reissue(bearerToken));
    }

}

