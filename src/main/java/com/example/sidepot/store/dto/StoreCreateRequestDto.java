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

    private String tardyTime;

    private String earlyLeaveTime;


    public StoreCreateRequestDto(final String storeName,
                                 final String branchName,
                                 final String primaryAdrress,
                                 final String detailAddress,
                                 final String storeClassifiacation,
                                 final String tardyTime,
                                 final String earlyLeaveTime){
        this.storeName = storeName;
        this.branchName = branchName;
        this.primaryAdrress = primaryAdrress;
        this.detailAddress = detailAddress;
        this.storeClassifiacation = storeClassifiacation;
        this.tardyTime = tardyTime;
        this.earlyLeaveTime = earlyLeaveTime;
    }

}
