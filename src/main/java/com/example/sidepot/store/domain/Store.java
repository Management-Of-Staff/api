package com.example.sidepot.store.domain;

import com.example.sidepot.store.dto.StoreCreateRequestDto;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "store")
@NoArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long ownerId;

    @NotNull
    private String storeName;

    private String storePhone;

    @NotNull
    private String primaryAddress;

    private String branchName;

    private String detailAddress;

    @NotNull
//    @Column(name = "store_classification")
//    @Enumerated(EnumType.STRING)
    private String storeClassification;

    @NotNull
    private String tardyTime;

    @NotNull
    private String earlyLeaveTime;

    public Store(Long ownerId, String storeName, String detailAddress, String branchName, String earlyLeaveTime, String primaryAddress, String storeClassifiacation, String tardyTime) {
        this.ownerId = ownerId;
        this.storeName = storeName;
        this.branchName = branchName;
        this.detailAddress = detailAddress;
        this.earlyLeaveTime = earlyLeaveTime;
        this.tardyTime = tardyTime;
        this.primaryAddress = primaryAddress;
        this.storeClassification = storeClassifiacation;
    }

}
