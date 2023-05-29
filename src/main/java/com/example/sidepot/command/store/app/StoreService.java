package com.example.sidepot.command.store.app;

import com.example.sidepot.command.member.domain.Owner;
import com.example.sidepot.command.member.domain.OwnerRepository;
import com.example.sidepot.command.store.domain.Store;
import com.example.sidepot.command.store.domain.StoreRepository;
import com.example.sidepot.command.store.dto.StoreCreateRequestDto;
import com.example.sidepot.command.store.dto.StoreDetailResponseDto;
import com.example.sidepot.command.store.dto.StoreResponseDto;
import com.example.sidepot.global.dto.ResponseDto;
import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.global.security.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class StoreService {
    private final StoreRepository storeRepository;
    private final OwnerRepository ownerRepository;

    public ResponseDto createStore(LoginMember member, StoreCreateRequestDto storeCreateRequestDto){
        Owner owner = ownerRepository.findByMemberId(member.getMemberId());
        storeRepository.save(new Store(owner,
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

    public ResponseDto readAllStore(LoginMember member){
        Stream<StoreResponseDto> storeDetailResponseDtoStream =
                ownerRepository.findByMemberId(member.getMemberId()).getStoreList().stream()
                    .map(StoreResponseDto::from);
        return ResponseDto.builder()
                .path(String.format("rest/v1/stores"))
                .method("GET")
                .message(String.format("매장 조회 성공"))
                .statusCode(200)
                .data(storeDetailResponseDtoStream)
                .build();
    }

    public ResponseDto readOneStore(LoginMember member, Long storeId){
        StoreDetailResponseDto storeDetailResponseDto =
                ownerRepository.findByMemberId(member.getMemberId()).getStoreList().stream()
                    .filter(store -> store.getStoreId().equals(storeId))
                    .map(StoreDetailResponseDto::from)
                    .findFirst()
                    .orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND_STORE));
        return ResponseDto.builder()
                .path(String.format("rest/v1/stores"))
                .method("GET")
                .message(String.format("매장 상세 조회 성공"))
                .statusCode(200)
                .data(storeDetailResponseDto)
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