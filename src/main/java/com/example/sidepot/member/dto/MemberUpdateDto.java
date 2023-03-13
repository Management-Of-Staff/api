package com.example.sidepot.member.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

public class MemberUpdateDto {
    @Data
    @NoArgsConstructor
    @ApiModel(value = "MemberUpdatePasswordRequestDto")
    public static class MemberUpdatePasswordRequestDto {
        private String newPassword;
    }

    @Data
    @NoArgsConstructor
    @ApiModel(value = "MemberCheckPasswordRequestDto")
    public static class MemberCheckPasswordRequestDto{
        private String password;
    }

    @Data
    @NoArgsConstructor
    @ApiModel(value = "MemberUpdatePhoneRequestDto")
    public static class MemberUpdatePhoneRequestDto {
        private String phoneNum;
    }

    @Getter
    @NoArgsConstructor
    @ApiModel(value = "MemberUpdateProfileRequestDto")
    public static class MemberUpdateProfileRequestDto {
        private String birthDate;
        private String email;
    }
}
