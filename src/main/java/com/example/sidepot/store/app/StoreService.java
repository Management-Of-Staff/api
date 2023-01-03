package com.example.sidepot.store.app;

import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.member.domain.Auth;
import com.example.sidepot.store.domain.Store;
import com.example.sidepot.store.domain.StoreRepository;
import com.example.sidepot.store.dto.StoreCreateRequestDto;
import com.example.sidepot.store.dto.StoreResponseDto;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public ResponseDto createStore(Auth auth, StoreCreateRequestDto storeCreateRequestDto){

        storeRepository.save(new Store(auth.getAuthId(),
                storeCreateRequestDto.getStoreName(),
                storeCreateRequestDto.getDetailAddress(),
                storeCreateRequestDto.getBranchName(),
                storeCreateRequestDto.getEarlyLeaveTime(),
                storeCreateRequestDto.getPrimaryAddress(),
                storeCreateRequestDto.getStoreClassifiacation(),
                storeCreateRequestDto.getLateTime()));

        return ResponseDto.builder()
                .path(String.format("rest/v1/stores"))
                .method("POST")
                .message(String.format("매장 생성 성공"))
                .statusCode(200)
                .data("")
                .build();
    }

    public List<StoreResponseDto> readAllStore(Auth auth){
        List<Store> storeList = storeRepository.findAllByOwnerId(auth.getAuthId());
        return StoreResponseDto.fromList(storeList);
    }

    public ResponseDto removeStore(Long storeId){
        storeRepository.deleteById(storeId);
        return ResponseDto.builder()
                .path(String.format("rest/v1/stores"))
                .method("DELETE")
                .message(String.format("매장 삭제 성공"))
                .statusCode(200)
                .data("")
                .build();
    }

}
