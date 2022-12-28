package com.example.sidepot.member.presentation;

import com.example.sidepot.global.Path;
import com.example.sidepot.member.app.OwnerService;
import com.example.sidepot.member.dto.MemberDto.OwnerDto;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class OwnerController {

    private final OwnerService ownerService;

    @ApiOperation("사장 회원 가입")
    @PostMapping(Path.REST_BASE_PATH + "/owner/register")
    public ResponseEntity<?> register(@RequestBody OwnerDto ownerDto) {
        return ResponseEntity.ok().body(ownerService.register(ownerDto));
    }

    @GetMapping(Path.REST_BASE_PATH + "/owner/a")
    public ResponseEntity<?> a(){
        return ResponseEntity.ok().body("test");
    }
}
