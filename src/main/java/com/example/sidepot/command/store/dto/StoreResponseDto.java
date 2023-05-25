package com.example.sidepot.command.store.dto;

import com.example.sidepot.command.store.domain.Store;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class StoreResponseDto {
    private Long storeId;
    private String storeName;
    private String branchName;

    public StoreResponseDto(Long storeId, String storeName, String branchName) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.branchName = branchName;
    }

    public static StoreResponseDto from(Store store){
        return new StoreResponseDto(store.getStoreId(), store.getStoreName(), store.getBranchName());
    }

    public static List<StoreResponseDto> fromList(List<Store> storeList){
        return storeList.stream()
                .map(StoreResponseDto::from)
                .collect(Collectors.toList());
    }
}
