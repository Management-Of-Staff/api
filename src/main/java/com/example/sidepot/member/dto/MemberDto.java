package com.example.sidepot.member.dto;

import com.example.sidepot.member.domain.Owner;
import com.example.sidepot.member.domain.Role;
import com.example.sidepot.member.domain.Staff;
import io.swagger.annotations.ApiModel;
import lombok.*;


public class MemberDto {

    @Data
    @NoArgsConstructor
    @ApiModel(value = "OwnerDto")
    public static class OwnerDto{

        private Long id;
        private String name;
        private String phone;
        private String password;
        private Role role;

        @Builder
        public OwnerDto(Long id, String name, String phone, String password, Role role) {
            this.id = id;
            this.name = name;
            this.phone = phone;
            this.password = password;
            this.role = role;
        }

        public static OwnerDto from(Owner owner) {
            return new OwnerDto(owner.getAuthId(), owner.getName(), owner.getPhone(),owner.getPassword(), owner.getRole());

        }
    }

    @Setter
    @Getter
    @NoArgsConstructor
    @ApiModel(value = "StaffDto")
    public static class StaffDto {

        private Long id;
        private String name;
        private String phone;
        private String password;
        private Role role;

        @Builder // 빌더 또는 of 명명법
        public StaffDto(Long id, String name, String phone, String password, Role role) {
            this.id = id;
            this.name = name;
            this.phone = phone;
            this.password = password;
            this.role = role;
        }

        public static StaffDto from(Staff staff){
            return new StaffDto(staff.getAuthId(),staff.getName(), staff.getPhone(), staff.getPassword(), staff.getRole());
        }
    }

    @Data
    @NoArgsConstructor
    @ApiModel(value = "MemberUpdateDto")
    public static class MemberUpdateDto{
        private String name;
        private String password;

        @Builder
        public MemberUpdateDto(String name,  String password) {
            this.name = name;
            this.password = password;
        }

        public static MemberUpdateDto of(String name, String password){
            return new MemberUpdateDto(name, password);
        }
    }
}
