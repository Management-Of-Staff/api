package com.example.sidepot.member.presentation;

import com.example.sidepot.global.Path;
import com.example.sidepot.member.app.OwnerService;
import com.example.sidepot.member.domain.Auth;
import com.example.sidepot.member.dto.MemberDto.MemberUpdateDto;
import com.example.sidepot.member.dto.MemberDto.OwnerDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


@Slf4j
@Api(tags = "매장 관련 APIs")
@RequestMapping(value = Path.REST_BASE_PATH + "/owners")
@RequiredArgsConstructor
@RestController
public class OwnerController {

    private final OwnerService ownerService;
    @ApiOperation(value = "회원가입", notes = "사장님 회원 가입")
    @ApiResponses({@ApiResponse(code = 200, message = "회원가입 완료"),
                   @ApiResponse(code = 400, message = "제대로 기입"),
                   @ApiResponse(code = 403, message = "권한 없음")})
    @PostMapping(value = "/register")
    public ResponseEntity<?> registerOwner(@RequestBody OwnerDto ownerDto) {
        return ResponseEntity.ok().body(ownerService.registerOwner(ownerDto));
    }


    @ApiOperation(value = "정보 조회", notes = "사장 마이페이지")
    @ApiResponses({@ApiResponse(code = 200, message = "사장 DB id"),
                   @ApiResponse(code = 403, message = "권한 없음")})
    @GetMapping(value ="/")
    public ResponseEntity<?> readOwnerInfo(@ApiIgnore @AuthenticationPrincipal Auth auth) {
        return ResponseEntity.ok().body(ownerService.readOwnerInfo(auth));
    }
    @ApiOperation(value = "정보수정", notes = "사장 정보 수정")
    @ApiResponses({@ApiResponse(code = 200, message = "정보 수정 완료"),
                   @ApiResponse(code = 403, message = "권한 없음")})
    @PostMapping(value = "/")
    public ResponseEntity<?> updateOwner(@RequestBody MemberUpdateDto memberUpdateDto,
                                    @ApiIgnore @AuthenticationPrincipal Auth auth){


        return ResponseEntity.ok().body(ownerService.updateOwnerInfo(memberUpdateDto,auth));
    }

    @ApiOperation(value = "회원탈퇴", notes = "사장 회원탈퇴")
    @ApiResponses({@ApiResponse(code = 200, message = "회원 탈퇴 완료"),
                   @ApiResponse(code = 403, message = "권한 없음")})
    @PutMapping (value = "/")
    public ResponseEntity deleteOwner(@ApiIgnore @AuthenticationPrincipal Auth auth){
        ownerService.deleteOwner(auth);
        return ResponseEntity.ok().build();
    }
}
