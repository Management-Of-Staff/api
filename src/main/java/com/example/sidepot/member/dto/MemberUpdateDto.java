package com.example.sidepot.member.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

public class MemberUpdateDto {
    @Data
    @NoArgsConstructor
    @ApiModel(value = "MemberUpdatePasswordRequestDto")
    public static class MemberUpdatePasswordRequestDto {

        private String oldPassword;
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

        String UUID;
        String phone;
    }

    @Data
    @NoArgsConstructor
    @ApiModel(value = "MemberUpdateProfileRequestDto")
    public static class MemberUpdateProfileRequestDto {

        private Date birthDate;
        private String Email;
    }
}
