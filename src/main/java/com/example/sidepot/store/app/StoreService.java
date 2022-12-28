package com.example.sidepot.store.app;

import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.member.dto.MemberDto;
import com.example.sidepot.store.domain.Store;
import com.example.sidepot.store.domain.StoreRepository;
import com.example.sidepot.store.dto.StoreCreateRequestDto;
import com.example.sidepot.store.dto.StoreRequestDto;
import com.example.sidepot.store.dto.StoreResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public ResponseDto createStore(StoreCreateRequestDto storeCreateRequestDto){

        storeRepository.save(new Store(storeCreateRequestDto.getOwnerId(),
                storeCreateRequestDto.getStoreName(),
                storeCreateRequestDto.getDetailAddress(),
                storeCreateRequestDto.getBranchName(),
                storeCreateRequestDto.getEarlyLeaveTime(),
                storeCreateRequestDto.getPrimaryAdrress(),
                storeCreateRequestDto.getStoreClassifiacation(),
                storeCreateRequestDto.getTardyTime()));

        return ResponseDto.builder()
                .path(String.format("api/v1/store/create"))
                .method("POST")
                .message(String.format("매장 생성 성공"))
                .statusCode(200)
                .data("")
                .build();
    }

    public List<StoreResponseDto> readAllStore(MemberDto.OwnerDto ownerDto){
        List<Store> storeList = storeRepository.findAllByOwnerId(ownerDto.getOwnerId());
        return StoreResponseDto.fromList(storeList);
    }

    public ResponseDto removeStore(Long storeId){
        storeRepository.deleteById(storeId);
        return ResponseDto.builder()
                .path(String.format("api/v1/store/remove"))
                .method("GET")
                .message(String.format("매장 삭제 성공"))
                .statusCode(200)
                .data("")
                .build();
    }

}
