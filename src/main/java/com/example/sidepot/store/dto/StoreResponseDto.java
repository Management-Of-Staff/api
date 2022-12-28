package com.example.sidepot.store.dto;

import com.example.sidepot.store.domain.Store;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class StoreResponseDto {
    private Long storeId;
    private String storeName;
    private String primaryAddress;

    public StoreResponseDto(Long id, String storeName, String primaryAdrress) {
        this.storeId = id;
        this.storeName = storeName;
        this.primaryAddress = primaryAdrress;
    }

    public static StoreResponseDto from(Store store){
        return new StoreResponseDto(store.getId(), store.getStoreName(), store.getPrimaryAdrress());
    }

    public static List<StoreResponseDto> fromList(List<Store> storeList){
        return storeList.stream()
                .map(StoreResponseDto::from)
                .collect(Collectors.toList());
    }
}
