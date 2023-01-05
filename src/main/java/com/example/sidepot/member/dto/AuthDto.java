package com.example.sidepot.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.*;

public class AuthDto {

    @Getter
    @NoArgsConstructor
    @ApiModel(value = "MemberLoginDto")
    public static class MemberLoginDto{
        @ApiParam(value = "사용자 ID(핸드폰번호)", required = true)
        private String phone;
        @ApiParam(value = "사용자 비밀번호", required = true)
        private String password;
    }

    @Getter
    @ApiModel(value = "tokenDto")
    public static class TokenDto{

        private String accessToken;
        private String refreshToken;
        @Builder
        public TokenDto(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }

        public static TokenDto of(String accessToken, String refreshToken){
            return new TokenDto(accessToken,refreshToken);
        }
    }
}
