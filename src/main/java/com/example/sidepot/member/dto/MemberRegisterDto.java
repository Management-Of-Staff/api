package com.example.sidepot.member.dto;

import com.example.sidepot.member.domain.Auth;
import com.example.sidepot.member.domain.Role;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


public class MemberRegisterDto {

    @Data
    @NoArgsConstructor
    @ApiModel(value = "MemberRegisterResponseDto")
    public static class MemberRegisterResponseDto {
        private String name;
        private String phone;
        private String uuid;
        private Role role;


        @Builder
        public MemberRegisterResponseDto(String name, String phone, String uuid, Role role) {
            this.name = name;
            this.phone = phone;
            this.role = role;
            this.uuid = uuid;
        }

        public static MemberRegisterResponseDto from(Auth auth ) {
            return new MemberRegisterResponseDto(auth.getName(), auth.getPhone(),auth.getUUID(), auth.getRole());
        }
    }

    @Data
    @NoArgsConstructor
    @ApiModel(value = "MemberRegisterRequestDto")
    public static class MemberRegisterRequestDto {
        private String uuid;
        private String name;
        private String phone;
        private String password;
        private Role role;

        private LocalDateTime createDate = LocalDateTime.now();

        @Builder
        public MemberRegisterRequestDto(String uuid, String name, String phone, String password, Role role) {
            this.uuid = uuid;
            this.name = name;
            this.phone = phone;
            this.password = password;
            this.role = role;
        }

//        public static MemberRegisterRequestDto from(Owner owner) {
//            return MemberRegisterRequestDto(owner.getAuthId(), owner.getName(), owner.getPhone(),owner.getPassword(), owner.getRole());
//
//        }
    }
}
