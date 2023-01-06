package com.example.sidepot.member.dto;

import com.example.sidepot.member.domain.Owner;
import com.example.sidepot.member.domain.Role;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.Date;


public class MemberRegisterDto {

    @Data
    @NoArgsConstructor
    @ApiModel(value = "MemberRegisterResponseDto")
    public static class MemberRegisterResponseDto {

        private Long id;
        private Long UUID;
        private String name;
        private String phone;
        private Role role;
        private String email;
        private Date birthDate;

        @Builder
        public MemberRegisterResponseDto(Long id, Long UUID, String name, String phone, Role role, String email, Date birthDate) {
            this.id = id;
            this.UUID = UUID;
            this.name = name;
            this.phone = phone;
            this.role = role;
            this.email = email;
            this.birthDate = birthDate;
        }

        public static MemberRegisterResponseDto from(Owner owner) {
            return null ; //new OwnerRegisterResponseDto(owner.getId(), owner.getName(), owner.getPhone(),owner.getPassword(), owner.getRole());
        }
    }

    @Data
    @NoArgsConstructor
    @ApiModel(value = "MemberRegisterRequestDto")
    public static class MemberRegisterRequestDto {
        private String name;
        private String phone;
        private String password;
        private Role role;

        @Builder
        public MemberRegisterRequestDto(Long id, String name, String phone, String password, Role role) {
            this.name = name;
            this.phone = phone;
            this.password = password;
            this.role = role;
        }

        public static MemberRegisterRequestDto from(Owner owner) {
            return null; //MemberRegisterRequestDto(owner.getId(), owner.getName(), owner.getPhone(),owner.getPassword(), owner.getRole());

        }
    }
}
