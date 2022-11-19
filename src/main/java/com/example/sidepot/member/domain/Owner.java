package com.example.sidepot.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@RequiredArgsConstructor
@Entity
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ownerId;
    private String name;
    private String phone;
    private String password;

    public Owner(String name, String phone, String password) {
        this.name = name;
        this.phone = phone;
        this.password = password;
    }

    public static Owner of(String name, String phone, String password){
        return new Owner(name, phone, password);
    }
}
