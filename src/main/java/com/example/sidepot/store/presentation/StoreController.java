package com.example.sidepot.store.presentation;

import com.example.sidepot.global.Path;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.store.app.StoreService;
import com.example.sidepot.store.dto.StoreCreateRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(tags = "매장 관련 APIs")
@RequestMapping(Path.REST_BASE_PATH)
public class StoreController {

    private final StoreService storeService;
    //private final AttendanceService attendanceService;

    @GetMapping("/stores")
    @ApiOperation(value = "[매장관리] 1. 매장 리스트 조회", notes = "오너가 가진 모든 매장을 조회하는 API")
    public ResponseEntity readStore(@ApiIgnore @AuthenticationPrincipal final LoginMember member){
        return ResponseEntity.ok(storeService.readAllStore(member));
    }


    @PostMapping("/stores")
    @ApiOperation(value = "[매장관리] 2. 매장 생성", notes = "오너가 가진 매장을 추가하는 API")
    public ResponseEntity createStore(@ApiIgnore @AuthenticationPrincipal final LoginMember member,
            @RequestBody StoreCreateRequestDto storeCreateRequestDto){
        return ResponseEntity.ok(storeService.createStore(member, storeCreateRequestDto));
    }

    @DeleteMapping("/stores/{storeId}")
    @ApiOperation(value = "[매장관리] 3. 매장 삭제", notes = "오너가 가진 매장을 삭제하는 API")
    public ResponseEntity deleteStore(@ApiIgnore @AuthenticationPrincipal final LoginMember member,
                                      @PathVariable final Long storeId){
        return ResponseEntity.ok(storeService.deleteStore(storeId));
    }

    @PostMapping("/stores/{storeId}")
    @ApiOperation(value = "[매장관리] 4. 매장 수정", notes = "오너가 가진 매장을 수정하는 API")
    public ResponseEntity updateStore(@ApiIgnore @AuthenticationPrincipal final LoginMember member,
                                      @PathVariable final Long storeId,
                                      @RequestBody StoreCreateRequestDto storeCreateRequestDto){
        return ResponseEntity.ok(storeService.updateStore(storeId, storeCreateRequestDto ));
    }

    @GetMapping("/stores/{storeId}")
    @ApiOperation(value = "[매장관리] 5. 매장 상세 조회", notes = "오너가 가진 매장을 상세 조회하는 API")
    public ResponseEntity findStore(@ApiIgnore @AuthenticationPrincipal final LoginMember member,
                                    @PathVariable final Long storeId
    ){
        return ResponseEntity.ok(storeService.readOneStore(member, storeId));
    }

//    @GetMapping("/stores/{storeId}/attendances")
//    @ApiOperation(value = "[매장관리] 6. 출석 직원 조회", notes = "매장에 출석한 직원을 조회하는 API")
//    public List<EmployeeAttendanceDto> getCurrentAttendance(@PathVariable Long storeId) {
//        return attendanceService.getCurrentAttendanceList(storeId);
//    }
}
