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
    public ResponseEntity<?> register(@RequestBody MemberDto.StaffDto staffDto) {
        return ResponseEntity.ok().body(staffService.register(staffDto));
    }
}
