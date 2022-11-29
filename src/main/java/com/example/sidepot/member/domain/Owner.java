package com.example.sidepot.member.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor  //기본 생성자
@Getter
@Entity
@Table(name = "owner",uniqueConstraints = {@UniqueConstraint(
        name = "name_phone_unique",
        columnNames = {"name", "phone"} )})
public class Owner {

    @Id @Column(name = "owner_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ownerId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone", nullable = false )
    private String phone;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "owner")
    private List<Store> storeList = new ArrayList<>();

    public Owner(String name, String phone, String password) {
        this.name = name;
        this.phone = phone;
        this.password = password;
    }

    public static Owner of(String name, String phone, String password){
        return new Owner(name, phone, password);
    }

}
