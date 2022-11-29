package com.example.sidepot.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Staff {

    private long staffId;
    private String name;
    private String phone;
    private String password;
}
