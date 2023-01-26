package com.example.sidepot.store.app;

import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.member.domain.Auth;
import com.example.sidepot.store.domain.Store;
import com.example.sidepot.store.domain.StoreRepository;
import com.example.sidepot.store.dto.StoreResponseDto;
import com.example.sidepot.store.dto.StoreCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StoreService {
    private final StoreRepository storeRepository;

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

    public ResponseDto readAllStore(Auth auth){
        List<Store> storeList = storeRepository.findAllByOwnerId(auth.getAuthId());
        return ResponseDto.builder()
                .path(String.format("rest/v1/stores"))
                .method("GET")
                .message(String.format("매장 조회 성공"))
                .statusCode(200)
                .data(StoreResponseDto.fromList(storeList))
                .build();
    }

    @Transactional
    public ResponseDto deleteStore(Long storeId){
        storeRepository.deleteByStoreId(storeId);
        return ResponseDto.builder()
                .path(String.format("rest/v1/stores"))
                .method("DELETE")
                .message(String.format("매장 삭제 성공"))
                .statusCode(200)
                .data("")
                .build();
    }

    @Transactional
    public ResponseDto updateStore(Long storeId, StoreCreateRequestDto storeCreateRequestDto){
        Store store = storeRepository.getByStoreId(storeId).orElseThrow(()-> new Exception(ErrorCode.NOT_FOUND_STORE));
        store.update(storeCreateRequestDto);
        storeRepository.save(store);
        return ResponseDto.builder()
                .path(String.format("rest/v1/stores"))
                .method("PUT")
                .message(String.format("매장 수정 성공"))
                .statusCode(200)
                .data("")
                .build();
    }

}
