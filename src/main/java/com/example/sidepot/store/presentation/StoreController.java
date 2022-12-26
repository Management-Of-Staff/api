package com.example.sidepot.store.presentation;

import com.example.sidepot.store.app.StoreService;
import com.example.sidepot.store.dto.StoreCreateRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/store")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody StoreCreateRequestDto storeCreateRequestDto){
        return ResponseEntity.ok(storeService.createStore(storeCreateRequestDto));
    }
}
