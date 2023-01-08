package com.example.sidepot.member.dto;

import com.example.sidepot.member.domain.Owner;
import com.example.sidepot.member.domain.Role;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



public class MemberRegisterDto {

    @Data
    @NoArgsConstructor
    @ApiModel(value = "MemberRegisterResponseDto")
    public static class MemberRegisterResponseDto {

        private String name;
        private String phone;
        private Role role;
        private Long uuid;

        @Builder
        public MemberRegisterResponseDto(String name, String phone, Long uuid, Role role) {
            this.name = name;
            this.phone = phone;
            this.role = role;
            this.uuid = uuid;
        }

//        public static MemberRegisterResponseDto from(Owner owner) {
//            return null ;
//            //new OwnerRegisterResponseDto(owner.getId(), owner.getName(), owner.getPhone(),owner.getPassword(), owner.getRole());
//        }
    }

    @Data
    @NoArgsConstructor
    @ApiModel(value = "MemberRegisterRequestDto")
    public static class MemberRegisterRequestDto {

        private Long uuid;
        private String name;
        private String phone;
        private String password;
        private Role role;

        @Builder
        public MemberRegisterRequestDto(Long uuid, String name, String phone, String password, Role role) {
            this.uuid = uuid;
            this.name = name;
            this.phone = phone;
            this.password = password;
            this.role = role;
        }

//        public static MemberRegisterRequestDto from(Owner owner) {
//            return null;
//            //MemberRegisterRequestDto(owner.getId(), owner.getName(), owner.getPhone(),owner.getPassword(), owner.getRole());
//
//        }
    }
}
