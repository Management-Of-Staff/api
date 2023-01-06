package com.example.sidepot.member.dto;

import com.example.sidepot.member.domain.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class MemberReadDto {
    @Getter
    @Setter
    @ApiModel(value = "MemberReadResponseDto")
    public static class MemberReadResponseDto{

        private Long UUID;
        private String name;
        private String phone;
        private String email;
        private Date birthDate;

        private Role role;

        public MemberReadResponseDto(String name, String phone, String email, Long UUID, Date birthDate, Role role) {
            this.name = name;
            this.phone = phone;
            this.email = email;
            this.UUID = UUID;
            this.birthDate = birthDate;
            this.role = role;
        }
    }
    @Getter
    @Setter
    @ApiOperation(value = "OwnerReadResponseDto")
    public static class OwnerReadResponseDto extends MemberReadResponseDto {

        private String something;

        @Builder
        public OwnerReadResponseDto(String name, String phone, String email, Long UUID, Date birthDate, String something, Role role) {
            super(name, phone, email, UUID, birthDate, role);
            this.something = something;
        }
    }

    @Getter
    @Setter
    @ApiOperation(value ="StaffReadResponseDto")
    public static class StaffReadResponseDto extends MemberReadResponseDto {

        private String somethingElse;

        @Builder
        public StaffReadResponseDto(String name, String phone, String email, Long UUID, Date birthDate, String somethingElse, Role role) {
            super(name, phone, email, UUID, birthDate, role);
            this.somethingElse = somethingElse;
        }
    }

}
