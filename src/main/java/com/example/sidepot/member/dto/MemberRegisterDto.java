package com.example.sidepot.member.dto;

import com.example.sidepot.member.domain.Member;
import com.example.sidepot.member.domain.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
        private Role role;
        @ApiModelProperty(hidden = true)
        private LocalDateTime createDate = LocalDateTime.now();

        public MemberRegisterResponseDto(String name, String phone, Role role) {
            this.name = name;
            this.phone = phone;
            this.role = role;
        }

        public static MemberRegisterResponseDto of(Member member) {
            return new MemberRegisterResponseDto(member.getMemberName(), member.getMemberPhoneNum(), member.getRole());
        }
    }

    @Data
    @NoArgsConstructor
    @ApiModel(value = "MemberRegisterRequestDto")
    public static class MemberRegisterRequestDto {
        private String name;
        private String phone;
        private String password;
        @ApiModelProperty(hidden = true)
        private LocalDateTime createDate = LocalDateTime.now();

        public MemberRegisterRequestDto(String name, String phone, String password) {
            this.name = name;
            this.phone = phone;
            this.password = password;
        }
    }
}
