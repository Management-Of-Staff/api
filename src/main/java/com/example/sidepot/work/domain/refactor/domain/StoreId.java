package com.example.sidepot.work.domain.refactor.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Access(AccessType.FIELD)
@Embeddable
public class StoreId implements Serializable {

    @Column(name = "store_id" )
    private Long storeId;

    @Column(name = "store_name")
    private String storeName;

    protected StoreId() {
    }

    public StoreId(Long storeId, String storeName) {
        this.storeId = storeId;
        this.storeName = storeName;
    }

    public Long getStoreId() {
        return storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public static StoreId from(Long id, String name) {return new StoreId(id, name);}

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        StoreId storeId = (StoreId) obj;
        return Objects.equals(this.storeId, storeId.storeId)
                && Objects.equals(this.storeName, storeId.storeName);
    }
}
