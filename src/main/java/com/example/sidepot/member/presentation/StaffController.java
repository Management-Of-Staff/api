package com.example.sidepot.member.presentation;


import com.example.sidepot.global.Path;
import com.example.sidepot.member.app.StaffService;
import com.example.sidepot.member.domain.Auth;
import com.example.sidepot.member.dto.MemberRegisterDto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "회원 관련 APIs")
@RequiredArgsConstructor
@RequestMapping(Path.REST_BASE_PATH + "/staffs")
@RestController
public class StaffController {

    private final StaffService staffService;

    @ApiOperation(value = "회원가입", notes = "직원 회원 가입")
    @ApiResponses({@ApiResponse(code = 200, message = "회원가입 완료"),
            @ApiResponse(code = 400, message = "제대로 기입"),
            @ApiResponse(code = 403, message = "권한 없음")})
    @PostMapping(value = "/register")
    public ResponseEntity<MemberRegisterResponseDto> registerStaff(@RequestBody MemberRegisterRequestDto dto) {
        return ResponseEntity.ok().body(staffService.registerStaff(dto));
    }


    @ApiOperation(value = "정보 조회", notes = "직원 마이페이지")
    @ApiResponses({@ApiResponse(code = 200, message = "사장 DB id"),
            @ApiResponse(code = 403, message = "권한 없음")})
    @GetMapping(value ="/")
    public ResponseEntity<?> readStaff(@ApiIgnore @AuthenticationPrincipal Auth auth) {
        return ResponseEntity.ok().body(staffService.readStaff(auth));
    }

    @ApiOperation(value = "회원 탈퇴 완료 요청", notes = "DB 에서 삭제하기 위한 탈퇴 기능")
    @ApiResponses({@ApiResponse(code = 200, message = "회원 탈퇴 완료"),
            @ApiResponse(code = 403, message = "권한 없음")})
    @PutMapping(value = "/")
    public ResponseEntity deleteStaff(@ApiIgnore @AuthenticationPrincipal Auth auth){
        staffService.deleteStaff(auth);
        return ResponseEntity.ok().build();
    }
}
