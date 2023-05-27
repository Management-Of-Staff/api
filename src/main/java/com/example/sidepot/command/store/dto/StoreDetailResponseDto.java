package com.example.sidepot.command.store.dto;

import com.example.sidepot.command.store.domain.Store;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StoreDetailResponseDto {

    private Long storeId;

    private String storeName;

    private String branchName;

    private String primaryAddress;

    private String detailAddress;

    private String storeClassifiacation;

    private String lateTime;

    private String earlyLeaveTime;

    @Builder
    public StoreDetailResponseDto(Long storeId, String storeName, String branchName, String primaryAddress,
                                  String detailAddress, String storeClassifiacation, String lateTime,
                                  String earlyLeaveTime){
        this.storeId = storeId;
        this.storeName = storeName;
        this.branchName = branchName;
        this.primaryAddress = primaryAddress;
        this.detailAddress = detailAddress;
        this.storeClassifiacation = storeClassifiacation;
        this.lateTime = lateTime;
        this.earlyLeaveTime = earlyLeaveTime;
    }

    public static StoreDetailResponseDto from(Store store){
        return StoreDetailResponseDto.builder().storeId(store.getStoreId())
                .storeName(store.getStoreName())
                .primaryAddress(store.getPrimaryAddress())
                .detailAddress(store.getDetailAddress())
                .storeClassifiacation(store.getStoreClassification())
                .lateTime(store.getLateTime())
                .earlyLeaveTime(store.getEarlyLeaveTime())
                .build();
    }
}
