package com.example.sidepot.store.presentation;

import com.example.sidepot.global.Path;
import com.example.sidepot.member.domain.Role;
import com.example.sidepot.member.dto.MemberDto;
import com.example.sidepot.security.domain.Auth;
import com.example.sidepot.store.app.StoreService;
import com.example.sidepot.store.dto.StoreCreateRequestDto;
import com.example.sidepot.store.dto.StoreResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Controller
@Api(tags = "매장 관련 APIs")
@RequestMapping(Path.REST_BASE_PATH)
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/stores")
    @ApiOperation(value = "[매장관리] 1. 매장 리스트 조회", notes = "오너가 가진 모든 매장을 조회하는 API")
    public ResponseEntity<List<StoreResponseDto>> readStore(@ApiIgnore @AuthenticationPrincipal final Auth auth,
                                                            @RequestParam Long ownerId){
        return ResponseEntity.ok(storeService.readAllStore(auth, ownerId));
    }
    @PostMapping("/stores")
    @ApiOperation(value = "[매장관리] 2. 매장 생성", notes = "오너가 가진 매장을 추가하는 API")
    public ResponseEntity createStore(@ApiIgnore @AuthenticationPrincipal final Auth auth,
            @RequestBody StoreCreateRequestDto storeCreateRequestDto){
        return ResponseEntity.ok(storeService.createStore(storeCreateRequestDto));
    }

    @DeleteMapping("/stores/{storeId}")
    @ApiOperation(value = "[매장관리] 3. 매장 삭제", notes = "오너가 가진 매장을 삭제하는 API")
    public ResponseEntity removeStore(@ApiIgnore @AuthenticationPrincipal final Auth auth,
                                      @PathVariable final Long storeId){
        return ResponseEntity.ok(storeService.removeStore(storeId));
    }


}
