package com.example.sidepot.member.presentation;


import com.example.sidepot.global.Path;
import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.member.app.AuthService;
import com.example.sidepot.member.domain.Auth;
import com.example.sidepot.member.dto.AuthDto.MemberLoginDto;
import com.example.sidepot.member.dto.AuthDto.TokenDto;
import com.example.sidepot.member.dto.MemberUpdateDto.MemberCheckPasswordRequestDto;
import com.example.sidepot.member.dto.MemberUpdateDto.MemberUpdatePasswordRequestDto;
import com.example.sidepot.member.dto.MemberUpdateDto.MemberUpdatePhoneRequestDto;
import com.example.sidepot.member.dto.MemberUpdateDto.MemberUpdateProfileRequestDto;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDate;

@Api(tags = "회원 관련 APIs")
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

    @ApiOperation(value ="회원 정보 수정", notes = "마이페이지에서 회원 정보 수정")
    @PostMapping("/update-profile")
    public ResponseEntity<?> updateProfile(@ApiIgnore @AuthenticationPrincipal Auth auth,
                                           @RequestPart(value="image",required = false) MultipartFile image,
                                           @RequestPart(value="updateProfile", required = true) MemberUpdateProfileRequestDto dto){

        return ResponseEntity.ok().body(authService.updateMemberProfile(auth, dto));
    }

    @ApiOperation(value ="비밀 번호 수정", notes = "마이페이지에서 비밀번호 변경")
    @PostMapping("/update-password")
    public ResponseEntity<?> updateMemberPassword(@ApiIgnore @AuthenticationPrincipal Auth auth,
                                                  @RequestBody MemberUpdatePasswordRequestDto dto){
        return ResponseEntity.ok().body(authService.updateMemberPassword(auth, dto));
    }

    @ApiOperation(value ="핸드폰 번호 수정", notes = "마이페이지에서 핸드폰번호 변경")
    @PostMapping("/update-phone")
    public ResponseEntity<?> updateMemberPhone(@ApiIgnore @AuthenticationPrincipal Auth auth,
                                               @RequestBody MemberUpdatePhoneRequestDto dto){
        return ResponseEntity.ok().body(authService.updateMemberPhone(auth, dto));
    }

    @ApiOperation(value = "비밀번호 번호 확인", notes = "비밀번호 변경화면에서 현재 비밀번호 체크")
    @PostMapping("/check-password")
    public ResponseEntity<ResponseDto> checkMemberPassword(@ApiIgnore @AuthenticationPrincipal Auth auth,
                                                           @RequestBody MemberCheckPasswordRequestDto dto){

        return ResponseEntity.ok().body(authService.checkMemberPassword(auth.getId(), dto));
    }

    @ApiOperation(value = "회원 탈퇴", notes = "마이페이지에서 회원탈퇴 시 회원 탈퇴 요청")
    @PostMapping("/withdrawal-member")
    public ResponseEntity<ResponseDto> withdrawalMember(@ApiIgnore @AuthenticationPrincipal Auth auth,
                                                           @RequestParam LocalDate withdrawalDate){
        return ResponseEntity.ok().body(authService.withdrawalMember(auth, withdrawalDate));
    }
}

