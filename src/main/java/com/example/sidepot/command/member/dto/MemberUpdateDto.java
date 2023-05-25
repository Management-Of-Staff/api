package com.example.sidepot.command.member.dto;

import lombok.*;

public class MemberUpdateDto {
    @Getter
    public static class MemberUpdatePasswordRequestDto {
        private String newPassword;
    }

    @Getter
    public static class MemberCheckPasswordRequestDto{
        private String password;
    }

    @Getter
    public static class MemberUpdatePhoneRequestDto {
        private String phoneNum;
    }

    @Getter
    public static class MemberUpdateProfileRequestDto {
        private String birthDate;
        private String email;
    }
}
