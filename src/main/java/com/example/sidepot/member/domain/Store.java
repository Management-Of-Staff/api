package com.example.sidepotback.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Store {

    private long storeId;
    private long ownerId;
    private String businessNum;
    private String storeName;
    private String storePhone;
    private String storeAddress;

}

