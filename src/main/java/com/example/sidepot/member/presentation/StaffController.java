package com.example.sidepot.member.presentation;


import com.example.sidepot.global.Path;
import com.example.sidepot.member.app.StaffService;
import com.example.sidepot.member.dto.MemberDto.*;
import com.example.sidepot.member.domain.Auth;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RequestMapping(Path.REST_BASE_PATH + "/staffs")
@RestController
public class StaffController {

    private final StaffService staffService;

    @ApiOperation("직원 회원 가입")
    @PostMapping(Path.REST_BASE_PATH + "/create")
    public ResponseEntity<?> register(@RequestBody StaffDto staffDto) {
        return ResponseEntity.ok().body(staffService.create(staffDto));
    }

    @ApiOperation(value = "회원가입", notes = "직원 회원 가입")

    @ApiResponses({@ApiResponse(code = 200, message = "회원가입 완료"),
            @ApiResponse(code = 400, message = "제대로 기입"),
            @ApiResponse(code = 403, message = "권한 없음")})
    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody StaffDto staffDto) {
        return ResponseEntity.ok().body(staffService.create(staffDto));
    }


    @ApiOperation(value = "정보 조회", notes = "직원 마이페이지")
    @ApiResponses({@ApiResponse(code = 200, message = "사장 DB id"),
            @ApiResponse(code = 403, message = "권한 없음")})
    @GetMapping(value ="/my-page")
    public ResponseEntity<?> read(@ApiIgnore @AuthenticationPrincipal Auth auth) {
        return ResponseEntity.ok().body(staffService.read(auth));
    }
    @ApiOperation(value = "정보수정", notes = "직원 정보 수정")
//    @ApiImplicitParams({@ApiImplicitParam(name = "name", value = "이름", required = true),
//            @ApiImplicitParam(name = "password", value = "비밀번호", required = true)
//    })
    @ApiResponses({@ApiResponse(code = 200, message = "정보 수정 완료"),
            @ApiResponse(code = 403, message = "권한 없음")})
    @PostMapping(value = "/update")
    public ResponseEntity<?> update(@RequestBody MemberUpdateDto memberUpdateDto,
                                    @AuthenticationPrincipal Auth auth){

        return ResponseEntity.ok().body(staffService.update(memberUpdateDto, auth));
    }

    @ApiOperation(value = "회원탈퇴", notes = "사장 회원탈퇴")
    @ApiResponses({@ApiResponse(code = 200, message = "회원 탈퇴 완료"),
            @ApiResponse(code = 403, message = "권한 없음")})
    @DeleteMapping(value = "/delete")
    public ResponseEntity delete(@ApiIgnore @AuthenticationPrincipal Auth auth){
        staffService.delete(auth);
        return ResponseEntity.ok().build();
    }
}
