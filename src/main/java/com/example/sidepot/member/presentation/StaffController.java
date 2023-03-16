package com.example.sidepot.member.presentation;

import com.example.sidepot.global.Path;
import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.member.app.StaffService;
import com.example.sidepot.member.dto.MemberRegisterDto.*;
import com.example.sidepot.member.dto.MemberUpdateDto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
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
public class StaffController {
    private final StaffService staffService;

    @ApiOperation(value = "[직원] 정보 조회", notes = "직원 마이페이지 정보 조회 API")
    @GetMapping(value ="/staffs")
    public ResponseEntity<ResponseDto> readStaff(@ApiIgnore @AuthenticationPrincipal LoginMember member) {
        return ResponseEntity.ok(staffService.readStaff(member));
    }

    @ApiOperation(value = "[직원] 회원 탈퇴", notes = "직원 회원 탈퇴 요청 API")
    @PutMapping("/staffs")
    public ResponseEntity<ResponseDto> withdrawalMember(@ApiIgnore @AuthenticationPrincipal LoginMember member){
        return ResponseEntity.ok(staffService.withdrawalStaff(member));
    }

    @ApiOperation(value = "[직원] 회원 가입", notes = "직원 회원 가입 API")
    @PostMapping(value = "/staffs/sign-up")
    public ResponseEntity<ResponseDto> registerStaff(@RequestBody MemberRegisterRequestDto memberRegisterRequestDto) {
        return ResponseEntity.ok(staffService.registerStaff(memberRegisterRequestDto));
    }

    @ApiOperation(value = "[직원] 비밀번호 변경", notes = "직원 비밀번호 변경 API")
    @PostMapping(value ="/staffs/password")
    public ResponseEntity<ResponseDto> readOwner(@ApiIgnore @AuthenticationPrincipal LoginMember member,
                                                 MemberUpdatePasswordRequestDto updatePasswordRequestDto){
        return ResponseEntity.ok(staffService.updateStaffPassword(member, updatePasswordRequestDto));
    }

    @ApiOperation(value = "[직원] 프로필 정보 변경", notes = "직원 프로필 정보 변경 API")
    @PostMapping(value ="/staffs/profile")
    public ResponseEntity<ResponseDto> updateOwnerProfileInfo(
                            @ApiIgnore @AuthenticationPrincipal LoginMember member,
                            @RequestPart(value = "image", required = false) MultipartFile image,
                            @RequestPart(value = "profile") MemberUpdateProfileRequestDto memberUpdateProfileRequestDto
                            ) throws IOException {
        return ResponseEntity.ok(staffService.updateStaffProfile(member, image, memberUpdateProfileRequestDto));
    }

    @ApiOperation(value = "[직원] 비밀번호 확인", notes = "직원 비밀번호 확인 API")
    @PostMapping(value ="/staffs/password-check")
    public ResponseEntity<ResponseDto> checkOwnerPassword(
                            @ApiIgnore @AuthenticationPrincipal LoginMember member,
                            MemberCheckPasswordRequestDto memberCheckPasswordRequestDto){
        return ResponseEntity.ok(staffService.checkStaffPassword(member, memberCheckPasswordRequestDto));
    }

    @ApiOperation(value = "[직원] 핸드폰 번호 수정", notes = "직원 핸드폰 번호 수정 API")
    @PostMapping(value ="/staffs/phone")
    public ResponseEntity<ResponseDto> updateOwnerProfileInfo(
                            @ApiIgnore @AuthenticationPrincipal LoginMember member,
                            MemberUpdatePhoneRequestDto memberUpdatePhoneRequestDto){
        return ResponseEntity.ok(staffService.updateStaffPhone(member, memberUpdatePhoneRequestDto));
    }
}
