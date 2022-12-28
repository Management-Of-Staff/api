package com.example.sidepot.security.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

public class AuthDto {

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @NoArgsConstructor
    @ApiModel(value = "MemberLoginDto")
    public static class MemberLoginDto{
        private String phone;
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
