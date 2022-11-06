package com.example.sidepot.member.dto;

import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class AuthDto {

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ApiModel(value = "memberDto")
    public class MemberDto{
        private String name;
        private String phone;
        private String password;
    }

    public class TokenDto{
        private String refreshToken;
        private String accessToken;
    }
}
