package com.example.sidepot.security.presentaition;



import com.example.sidepot.global.Path;


import com.example.sidepot.security.app.AuthService;
import com.example.sidepot.security.dto.AuthDto.*;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RequestMapping(Path.REST_BASE_PATH + "/auth")
@RestController
public class AuthController {

    private final String AUTHORIZATION_HEADER = "Authorization";

    private final AuthService authService;

    @ApiOperation(value = "로그인", notes = "오너, 직원 로그인")
//    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "핸드폰 번호", required = true),
//                        @ApiImplicitParam(name = "password", value = "비밀번호", required = true)
//                        })
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

