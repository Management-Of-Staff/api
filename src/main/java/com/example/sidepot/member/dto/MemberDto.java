package com.example.sidepot.member.dto;

import com.example.sidepot.member.domain.Role;
import com.example.sidepot.member.domain.Owner;
import com.example.sidepot.member.domain.Staff;
import io.swagger.annotations.ApiModel;
import lombok.*;


public class MemberDto {

    @Setter
    @Getter
    @NoArgsConstructor
    @ApiModel(value = "OwnerDto")
    public static class OwnerDto{
        private String name;
        private String phone;
        private String password;
        private Role role;

        @Builder
        public OwnerDto(String name, String phone, String password, Role role) {
            this.name = name;
            this.phone = phone;
            this.password = password;
            this.role = role;
        }

        public static OwnerDto from(Owner owner) {
            return new OwnerDto(owner.getName(), owner.getPhone(),owner.getPassword(), owner.getRole());

        }
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @ApiModel(value = "StaffDto")
    public static class StaffDto {
        private String name;
        private String phone;
        private String password;
        private Role role;

        @Builder // 빌더 또는 of 명명법
        public StaffDto(String name, String phone, String password, Role role) {
            this.name = name;
            this.phone = phone;
            this.password = password;
            this.role = role;
        }

        public static StaffDto from(Staff staff){
            return new StaffDto(staff.getName(), staff.getPhone(), staff.getPassword(), staff.getRole());
        }
    }

    @Getter
    @NoArgsConstructor
    @ApiModel(value = "ResMemberLoginDto")
    public static class ResMemberLoginDto{
        private String name;
        private String phone;
        private String refreshToken;
        private String accessToken;

        @Builder
        public ResMemberLoginDto(String name, String phone, String refreshToken, String accessToken) {
            this.name = name;
            this.phone = phone;
            this.refreshToken = refreshToken;
            this.accessToken = accessToken;
        }

        public static ResMemberLoginDto of(String name, String phone, String refreshToken, String accessToken){
            return new ResMemberLoginDto(name, phone, refreshToken, accessToken);
        }
    }

    @Data
    @Getter
    @NoArgsConstructor
    @ApiModel(value = "ReqMemberLoginDto")
    public static class ReqMemberLoginDto{

        public enum LoginType{
            FORM, REFRESH
        }
        private String phone;
        private String password;
        private LoginType loginType;

        private String refreshToken;

        public ReqMemberLoginDto(String phone, String name){
            this.phone = phone;
            this.password = name;
        }

        public static ReqMemberLoginDto of(String phone, String password){
            return new ReqMemberLoginDto(phone, password);
        }
    }
}
