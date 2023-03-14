package com.example.sidepot.store.domain;


import com.example.sidepot.work.domain.Employment;
import com.example.sidepot.member.domain.Owner;
import com.example.sidepot.store.dto.StoreCreateRequestDto;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


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
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

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

    @Column(name = "employment_id")
    @OneToMany(mappedBy = "store")
    private List<Employment> employmentId;

    @OneToMany(mappedBy = "store")
    @Column(name = "todo_lists")
    private List<TodoList> todoLists = new ArrayList<>();

    @Builder
    public Store(Owner owner, String storeName, String detailAddress, String branchName, String earlyLeaveTime, String primaryAddress, String storeClassifiacation, String lateTime) {
        this.owner = owner;
        this.storeName = storeName;
        this.branchName = branchName;
        this.detailAddress = detailAddress;
        this.earlyLeaveTime = earlyLeaveTime;
        this.lateTime = lateTime;
        this.primaryAddress = primaryAddress;
        this.storeClassification = storeClassifiacation;
    }

    public void update(StoreCreateRequestDto storeCreateRequestDto){
        this.storeName = storeCreateRequestDto.getStoreName();
        this.branchName = storeCreateRequestDto.getBranchName();
        this.detailAddress = storeCreateRequestDto.getDetailAddress();
        this.earlyLeaveTime = storeCreateRequestDto.getEarlyLeaveTime();
        this.lateTime = storeCreateRequestDto.getLateTime();
        this.primaryAddress = storeCreateRequestDto.getPrimaryAddress();
        this.storeClassification = storeCreateRequestDto.getStoreClassifiacation();
    }
}
