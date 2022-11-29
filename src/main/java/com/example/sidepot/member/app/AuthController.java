package com.example.sidepot.member.app;

import com.example.sidepot.global.Path;
import com.example.sidepot.member.dto.MemberDto.OwnerDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @ApiOperation("사장 회원 가입")
    @PostMapping(Path.REST_BASE_PATH + "/owners/register")
    public ResponseEntity<OwnerDto> register(@RequestBody OwnerDto ownerDto) {
        return ResponseEntity.ok(authService.register(ownerDto));
    }
}
