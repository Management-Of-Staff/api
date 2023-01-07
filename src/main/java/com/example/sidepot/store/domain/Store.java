package com.example.sidepot.store.domain;


import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;



@Getter
@Table(name = "store")
@NoArgsConstructor
@Entity
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long storeId;

    @NotNull
    @Column(name = "owner_id")
    private Long ownerId;

    @NotNull
    @Column(name = "store_name")
    private String storeName;

    @Column(name = "store_phone")
    private String storePhone;

    @NotNull
    @Column(name = "primary_address")
    private String primaryAddress;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "detail_address")
    private String detailAddress;


    @NotNull
    @Column(name = "store_calssification")
//    @Enumerated(EnumType.STRING)
    private String storeClassification;

    @NotNull
    @Column(name = "late_time")
    private String lateTime;

    @NotNull
    @Column(name = "early_leave_time")
    private String earlyLeaveTime;

    public Store(Long ownerId, String storeName, String detailAddress, String branchName, String earlyLeaveTime, String primaryAddress, String storeClassifiacation, String lateTime) {
        this.ownerId = ownerId;
        this.storeName = storeName;
        this.branchName = branchName;
        this.detailAddress = detailAddress;
        this.earlyLeaveTime = earlyLeaveTime;
        this.lateTime = lateTime;
        this.primaryAddress = primaryAddress;
        this.storeClassification = storeClassifiacation;
    }
}
