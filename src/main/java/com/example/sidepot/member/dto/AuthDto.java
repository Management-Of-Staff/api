package com.example.sidepot.member.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

public class AuthDto {

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ApiModel(value = "MemberLoginDto")
    public class MemberLoginDto{
        private String phone;
        private String password;
    }

    @Getter
    @AllArgsConstructor
    @ApiModel(value = "tokenDto")
    public class TokenDto{
        private String accessToken;
        private String refreshToken;

    }

}
