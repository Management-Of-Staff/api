package com.example.sidepot.member.domain;

import com.example.sidepot.member.domain.owner.Owner;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@Table(name ="store")
public class Store {

    @Id @Column(name = "store_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long storeId;

    @Column(name = "business_num")
    private String businessNum;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "store_phone")
    private String storePhone;

    //임베디드 타입? 으로 빼면 프론트에서 처리하기 좋을 듯
    @Column(name = "store_address")
    private String storeAddress;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @OneToMany(mappedBy = "store")
    private List<Employment> staffStore;

}

