package com.example.sidepot.store.presentation;

import com.example.sidepot.global.Path;
import com.example.sidepot.member.dto.MemberDto;
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
@RequestMapping(Path.REST_BASE_PATH + "/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping("/read")
    @ApiOperation(value = "[매장관리] 1. 매장 리스트 조회", notes = "오너가 가진 모든 매장을 조회하는 API")
    public ResponseEntity<List<StoreResponseDto>> readStore(@ApiIgnore @AuthenticationPrincipal final MemberDto.OwnerDto ownerDto){
        return ResponseEntity.ok(storeService.readAllStore(ownerDto));
    }
    @PostMapping("/create")
    @ApiOperation(value = "[매장관리] 2. 매장 생성", notes = "오너가 가진 매장을 추가하는 API")
    public ResponseEntity createStore(@ApiIgnore @AuthenticationPrincipal final MemberDto.OwnerDto ownerDto,
            @RequestBody StoreCreateRequestDto storeCreateRequestDto){
        return ResponseEntity.ok(storeService.createStore(storeCreateRequestDto));
    }

    @PostMapping("/{storeId}/remove")
    @ApiOperation(value = "[매장관리] 3. 매장 삭제", notes = "오너가 가진 매장을 삭제하는 API")
    public ResponseEntity removeStore(@ApiIgnore @AuthenticationPrincipal final MemberDto.OwnerDto ownerDto,
                                      @PathVariable final Long storeId){
        return ResponseEntity.ok(storeService.removeStore(storeId));
    }


}
