package com.example.sidepot.store.domain;

import lombok.Getter;

@Getter
public enum StoreClassification {
    FOOD(1, "음식점"),
    CAFE(2, "카페"),
    CONVENIENCE_STORE(3, "편의점"),
    PC_ROOM(4, "PC방"),
    HEALTH_ROOM(5, "헬스장"),
    ACADEMY(6, "학원"),
    OFFICE_ASSISTANT(7, "사무보조"),
    OTHER(8,"기타");

    private final Integer storeNum;
    private final String storeType;
    StoreClassification(Integer storeNum, String storeType) {
        this.storeNum = storeNum;
        this.storeType = storeType;
    }

}
