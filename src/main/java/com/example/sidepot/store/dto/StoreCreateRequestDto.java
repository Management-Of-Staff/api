package com.example.sidepot.store.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StoreCreateRequestDto {

    private Long ownerId;

    private String storeName;

    private String branchName;

    private String primaryAdrress;

    private String detailAddress;

    private String storeClassifiacation;

    private String lateTime;

    private String earlyLeaveTime;


    public StoreCreateRequestDto(final String storeName,
                                 final String branchName,
                                 final String primaryAdrress,
                                 final String detailAddress,
                                 final String storeClassifiacation,
                                 final String lateTime,
                                 final String earlyLeaveTime){
        this.storeName = storeName;
        this.branchName = branchName;
        this.primaryAdrress = primaryAdrress;
        this.detailAddress = detailAddress;
        this.storeClassifiacation = storeClassifiacation;
        this.lateTime = lateTime;
        this.earlyLeaveTime = earlyLeaveTime;
    }

}
