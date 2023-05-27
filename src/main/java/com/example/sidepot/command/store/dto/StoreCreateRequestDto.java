package com.example.sidepot.command.store.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StoreCreateRequestDto {

    private String storeName;

    private String branchName;

    private String primaryAddress;

    private String detailAddress;

    private String storeClassifiacation;

    private String lateTime;

    private String earlyLeaveTime;


    @Builder
    public StoreCreateRequestDto(
                                 final String storeName,
                                 final String branchName,
                                 final String primaryAdrress,
                                 final String detailAddress,
                                 final String storeClassifiacation,
                                 final String lateTime,
                                 final String earlyLeaveTime){
        this.storeName = storeName;
        this.branchName = branchName;
        this.primaryAddress = primaryAdrress;
        this.detailAddress = detailAddress;
        this.storeClassifiacation = storeClassifiacation;
        this.lateTime = lateTime;
        this.earlyLeaveTime = earlyLeaveTime;
    }

}
