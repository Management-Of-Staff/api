package com.example.sidepot.global.security;

import lombok.Getter;

@Getter
public class LoginMember {
    private Long memberId;
    private String memberName;
    private String memberPhoneNum;

    public LoginMember(Long memberId, String memberName,String memberPhoneNum) {
        this.memberId = memberId;
        this.memberPhoneNum = memberPhoneNum;
    }
}
