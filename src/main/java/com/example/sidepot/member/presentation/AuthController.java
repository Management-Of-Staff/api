package com.example.sidepot.member.presentation;


import com.example.sidepot.global.Path;
import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.member.app.AuthService;
import com.example.sidepot.member.domain.Auth;
import com.example.sidepot.member.dto.AuthDto.*;
import com.example.sidepot.member.dto.MemberUpdateDto.*;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDate;

@Slf4j
@Api(tags = "회원 관련 APIs")
@RequiredArgsConstructor
@RequestMapping(Path.REST_BASE_PATH + "/auth")
@RestController
public class AuthController {

    private final String AUTHORIZATION_HEADER = "Authorization";

    private final AuthService authService;

    @ApiOperation(value = "[회원 관리] 1.로그인", notes = "통합 로그인 API")
    @ApiResponses({@ApiResponse(code = 200, message = "로그인 완료")})
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberLoginDto memberLoginDto) throws Throwable {
        return ResponseEntity.ok(authService.login(memberLoginDto));
    }


    @ApiOperation(value = "[회원 관리] 2.토큰 재발급", notes = "리프레시 토큰을 통한 토큰 재발급 API")
    @ApiImplicitParam(name = "refreshToken" , value = "리프레시 토큰", required = true,
                      dataType = "String", paramType = "header")
    @ApiResponses({@ApiResponse(code = 200, message = "토큰 재발급 성공")})
    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@ApiIgnore @RequestHeader(AUTHORIZATION_HEADER) String bearerToken) throws Throwable {
        return ResponseEntity.ok(authService.reissue(bearerToken));
    }

    @ApiOperation(value ="[회원 관리] 3.회원 정보 수정", notes = "마이페이지 회원정보 수정 API")
    @PostMapping(value = "/update-profile" , consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<ResponseDto> updateProfile(@ApiIgnore @AuthenticationPrincipal Auth auth,
                                           @RequestPart(value="image", required = false) MultipartFile image,
                                           @RequestPart(value="profile") MemberUpdateProfileRequestDto
                                                                         memberUpdateProfileRequestDto) throws Exception{
        return ResponseEntity.ok(authService.updateMemberProfile(auth, image, memberUpdateProfileRequestDto));
    }

    @ApiOperation(value ="[회원 관리] 4.비밀 번호 수정", notes = "마이페이지 비밀번호 변경 API")
    @PostMapping("/update-password")
    public ResponseEntity<ResponseDto> updateMemberPassword(@ApiIgnore @AuthenticationPrincipal Auth auth,
                                                            @RequestBody MemberUpdatePasswordRequestDto
                                                                    memberUpdatePasswordRequestDto){
        return ResponseEntity.ok(authService.updateMemberPassword(auth, memberUpdatePasswordRequestDto));
    }

    @ApiOperation(value ="[회원 관리] 5.핸드폰 번호 수정", notes = "마이페이지 핸드폰번호 변경 API")
    @PostMapping("/update-phone")
    public ResponseEntity<ResponseDto> updateMemberPhone(@ApiIgnore @AuthenticationPrincipal Auth auth,
                                                         @RequestBody MemberUpdatePhoneRequestDto
                                                         memberUpdatePhoneRequestDto){
        return ResponseEntity.ok(authService.updateMemberPhone(auth, memberUpdatePhoneRequestDto));
    }

    @ApiOperation(value = "[회원 관리] 6.비밀번호 번호 확인", notes = "비밀번호 변경 시 비밀번화 확인 API")
    @PostMapping("/check-password")
    public ResponseEntity<ResponseDto> checkMemberPassword(@ApiIgnore @AuthenticationPrincipal Auth auth,
                                                           @RequestBody MemberCheckPasswordRequestDto memberCheckPasswordRequestDto){
        return ResponseEntity.ok(authService.checkMemberPassword(auth.getAuthId(), memberCheckPasswordRequestDto));
    }

    @ApiOperation(value = "[회원 관리] 7.회원 탈퇴", notes = "마이페이지 회원 탈퇴 요청 API")
    @PostMapping("/withdrawal-member")
    public ResponseEntity<ResponseDto> withdrawalMember(@ApiIgnore @AuthenticationPrincipal Auth auth){
        return ResponseEntity.ok(authService.withdrawalMember(auth));
    }
}

