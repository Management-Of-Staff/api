package com.example.sidepot.member.presentation;

import com.example.sidepot.global.Path;
import com.example.sidepot.member.app.OwnerService;
import com.example.sidepot.member.dto.MemberDto;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class OwnerController {

    private final OwnerService ownerService;

    @ApiOperation("사장 회원 가입")
    @PostMapping(Path.REST_BASE_PATH + "/owners/register")
    public ResponseEntity<?> register(@RequestBody MemberDto.OwnerDto dto) {
        return ResponseEntity.ok().body(ownerService.register(dto));
    }

    @ApiOperation("사장 로그인")
    @PostMapping(Path.REST_BASE_PATH + "/owners/login")
    public ResponseEntity<?> login(@RequestBody MemberDto.ReqMemberLoginDto dto){
        return ownerService.login(dto);
    }


}
