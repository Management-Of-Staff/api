package com.example.sidepot.member.presentation;

import com.example.sidepot.global.Path;
import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.member.app.OwnerService;
import com.example.sidepot.member.dto.MemberRegisterDto.*;
import com.example.sidepot.member.dto.MemberUpdateDto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;


@Api(tags = "회원 관련 APIs")
@RequestMapping(value = Path.REST_BASE_PATH + "/member-account")
@RequiredArgsConstructor
@RestController
public class OwnerController {

    private final OwnerService ownerService;

    @ApiOperation(value = "[사장] 회원가입", notes = "사장님 회원 가입 API")
    @PostMapping(value = "/owner/sign-up")
    public ResponseEntity<ResponseDto> registerOwner(@RequestBody MemberRegisterRequestDto memberRegisterRequestDto){
        return ResponseEntity.ok(ownerService.registerOwner(memberRegisterRequestDto));
    }

    @ApiOperation(value = "[사장] 회원 탈퇴", notes = "사장 회원 탈퇴 요청 API")
    @PostMapping("/owner/withdrawal")
    public ResponseEntity<ResponseDto> withdrawalMember(@ApiIgnore @AuthenticationPrincipal LoginMember member){
        return ResponseEntity.ok(ownerService.withdrawalOwner(member));
    }

    @ApiOperation(value = "[사장] 정보 조회", notes = "사장 상세보기(마이페이지) API")
    @GetMapping(value ="/owner")
    public ResponseEntity<ResponseDto> readOwner(@ApiIgnore @AuthenticationPrincipal LoginMember member){
        return ResponseEntity.ok(ownerService.readOwner(member));
    }

    @ApiOperation(value = "[사장] 비밀번호 변경", notes = "사장 비밀번호 변경 API")
    @PostMapping(value ="/owner/password-update")
    public ResponseEntity<ResponseDto> updateOwnerPassword(
                            @ApiIgnore @AuthenticationPrincipal LoginMember member,
                            MemberUpdatePasswordRequestDto updatePasswordRequestDto
                            ){
        return ResponseEntity.ok(ownerService.updateOwnerPassword(member, updatePasswordRequestDto));
    }

    @ApiOperation(value = "[사장] 프로필 정보 변경", notes = "사장 프로필 정보 변경 API")
    @PostMapping(value ="/owner/profile-update")
    public ResponseEntity<ResponseDto> updateOwnerProfileInfo(
                            @ApiIgnore @AuthenticationPrincipal LoginMember member,
                            @RequestPart(value = "image", required = false) MultipartFile image,
                            @RequestPart(value = "profile") MemberUpdateProfileRequestDto memberUpdateProfileRequestDto
                            ) throws IOException {
        return ResponseEntity.ok(ownerService.updateOwnerProfile(member, image, memberUpdateProfileRequestDto));
    }

    @ApiOperation(value = "[사장] 비밀번호 확인", notes = "사장 비밀번호 확인 API")
    @PostMapping(value ="/owner/password-check")
    public ResponseEntity<ResponseDto> checkOwnerPassword(
                            @ApiIgnore @AuthenticationPrincipal LoginMember member,
                            MemberCheckPasswordRequestDto memberCheckPasswordRequestDto
                            ){
        return ResponseEntity.ok(ownerService.checkOwnerPassword(member, memberCheckPasswordRequestDto));
    }

    @ApiOperation(value = "[사장] 핸드폰 번호 수정 ", notes = "사장 핸드폰 번호 수정 API")
    @PostMapping(value ="/owner/phone-update]")
    public ResponseEntity<ResponseDto> updateOwnerProfileInfo(
                            @ApiIgnore @AuthenticationPrincipal LoginMember member,
                            MemberUpdatePhoneRequestDto memberUpdatePhoneRequestDto
                            ){
        return ResponseEntity.ok(ownerService.updateOwnerPhone(member, memberUpdatePhoneRequestDto));
    }

}
