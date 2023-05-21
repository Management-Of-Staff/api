package com.example.sidepot.work.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class StoreId {

    private Long storeId;
    private String branchName;
    private String storeName;

    public StoreId(Long storeId, String branchName, String storeName) {
        this.storeId = storeId;
        this.branchName = branchName;
        this.storeName = storeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreId storeId1 = (StoreId) o;
        return Objects.equals(storeId, storeId1.storeId) && Objects.equals(branchName, storeId1.branchName) && Objects.equals(storeName, storeId1.storeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeId, branchName, storeName);
    }
}
