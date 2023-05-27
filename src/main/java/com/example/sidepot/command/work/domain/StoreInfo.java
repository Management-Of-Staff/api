package com.example.sidepot.command.work.domain;

import com.example.sidepot.command.store.domain.Store;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class StoreInfo {

    private Long storeId;
    private String branchName;
    private String storeName;

    public StoreInfo(Long storeId, String branchName, String storeName) {
        this.storeId = storeId;
        this.branchName = branchName;
        this.storeName = storeName;
    }
    public StoreInfo(StoreInfo storeInfo){
        this.storeId = storeInfo.getStoreId();
        this.branchName = storeInfo.getBranchName();
        this.storeName = storeInfo.getStoreName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreInfo storeInfo1 = (StoreInfo) o;
        return Objects.equals(storeId, storeInfo1.storeId) && Objects.equals(branchName, storeInfo1.branchName) && Objects.equals(storeName, storeInfo1.storeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeId, branchName, storeName);
    }
}
