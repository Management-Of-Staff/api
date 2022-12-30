package com.example.sidepot.member.presentation;

import com.example.sidepot.global.Path;
import com.example.sidepot.member.app.OwnerService;
import com.example.sidepot.member.domain.Role;
import com.example.sidepot.member.dto.MemberDto.*;
import com.example.sidepot.security.domain.Auth;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


@Slf4j
@RequestMapping(value = Path.REST_BASE_PATH + "/owner")
@AllArgsConstructor
@RestController
public class OwnerController {

    private final OwnerService ownerService;
    @ApiOperation(value = "회원가입", notes = "사장님 회원 가입")
//    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "디비 id", required = false),
//                        @ApiImplicitParam(name = "name", value = "이름", required = true),
//                        @ApiImplicitParam(name = "password", value = "비밀번호", required = true),
//                        @ApiImplicitParam(name = "phone", value = "핸드폰번호", required = true),
//                        @ApiImplicitParam(name = "role", value = "권한(STAFF,ADMIN,OWNER)", required = true
//                                            , dataTypeClass = Role.class)
//                        })
    @ApiResponses({@ApiResponse(code = 200, message = "회원가입 완료"),
                   @ApiResponse(code = 400, message = "제대로 기입"),
                   @ApiResponse(code = 403, message = "권한 없음")})
    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody OwnerDto ownerDto) {
        return ResponseEntity.ok().body(ownerService.create(ownerDto));
    }


    @ApiOperation(value = "정보 조회", notes = "사장 마이페이지")
    @ApiResponses({@ApiResponse(code = 200, message = "사장 DB id"),
                   @ApiResponse(code = 403, message = "권한 없음")})
    @GetMapping(value ="/my-page")
    public ResponseEntity<?> read(@ApiIgnore @AuthenticationPrincipal Auth auth) {
        return ResponseEntity.ok().body(ownerService.read(auth));
    }
    @ApiOperation(value = "정보수정", notes = "사장 정보 수정")
    @ApiResponses({@ApiResponse(code = 200, message = "정보 수정 완료"),
                   @ApiResponse(code = 403, message = "권한 없음")})
    @PostMapping(value = "/update")
    public ResponseEntity<?> update(@RequestBody MemberUpdateDto memberUpdateDto,
                                    @ApiIgnore @AuthenticationPrincipal Auth auth){

        return ResponseEntity.ok().body(ownerService.update(memberUpdateDto,auth));
    }

    @ApiOperation(value = "회원탈퇴", notes = "사장 회원탈퇴")
    @ApiResponses({@ApiResponse(code = 200, message = "회원 탈퇴 완료"),
                   @ApiResponse(code = 403, message = "권한 없음")})
    @DeleteMapping (value = "/delete")
    public ResponseEntity delete(@ApiIgnore @AuthenticationPrincipal Auth auth){
        ownerService.delete(auth);
        return ResponseEntity.ok().build();
    }
}
