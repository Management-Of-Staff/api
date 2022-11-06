package com.example.sidepotback.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Owner {

    private long ownerId;
    private String name;
    private String phone;
    private String password;
}
