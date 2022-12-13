package com.example.sidepot.member.presentation;


import com.example.sidepot.global.Path;
import com.example.sidepot.member.app.StaffService;
import com.example.sidepot.member.dto.MemberDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StaffController {

    private final StaffService staffService;

    @ApiOperation("직원 회원 가입")
    @PostMapping(Path.REST_BASE_PATH + "/staff/register")
    public ResponseEntity<?> register(@RequestBody MemberDto.StaffDto dto) {
        return ResponseEntity.ok().body(staffService.register(dto));
    }

    @ApiOperation("직원 로그인")
    @PostMapping(Path.REST_BASE_PATH + "/staff/login")
    public ResponseEntity<?> login(@RequestBody MemberDto.ReqMemberLoginDto dto) throws Exception{

        return staffService.login(dto);
    }

    @GetMapping(Path.REST_BASE_PATH + "/staff/a")
    public ResponseEntity<?> a(){
        return ResponseEntity.ok().body("test");
    }

    @GetMapping(Path.REST_BASE_PATH + "/staff/b")
    public ResponseEntity<Authentication> b(Authentication authentication){
        return ResponseEntity.ok().body(authentication);
    }
}
