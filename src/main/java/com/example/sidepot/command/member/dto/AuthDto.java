package com.example.sidepot.command.member.dto;

import lombok.Getter;

public class AuthDto {

    @Getter
    public static class MemberLoginDto{
        private String phoneNum;
        private String password;
    }

    @Getter
    public static class TokenDto{

        private String accessToken;
        private String refreshToken;

        private TokenDto(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }

        public static TokenDto from(String accessToken, String refreshToken){
            return new TokenDto(accessToken, refreshToken);
        }
    }
}
