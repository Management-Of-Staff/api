package com.example.sidepot.member.dto;

import com.example.sidepot.member.domain.Member;
import com.example.sidepot.member.domain.Owner;
import com.example.sidepot.member.domain.Role;
import com.example.sidepot.member.domain.Staff;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;


public class MemberReadDto {
    @Getter
    @Setter
    @ApiModel(value = "MemberReadResponseDto")
    public static class MemberReadResponseDto{
        private String name;
        private String phone;
        private String email;
        private LocalDate birthDate;
        private Role role;

        public MemberReadResponseDto(String name, String phone, String email, LocalDate birthDate, Role role) {
            this.name = name;
            this.phone = phone;
            this.email = email;
            this.birthDate = birthDate;
            this.role = role;
        }

        public static MemberReadResponseDto from(Member member){
            return new MemberReadResponseDto(member.getMemberName(),
                                             member.getMemberPhoneNum(),
                                             member.getEmail(),
                                             member.getBirthDate(),
                                             member.getRole());
        }
    }
    @Getter
    @Setter
    @ApiOperation(value = "OwnerReadResponseDto")
    public static class OwnerReadResponseDto extends MemberReadResponseDto {

        @Builder
        public OwnerReadResponseDto(String name, String phone, String email, LocalDate birthDate, Role role) {
            super(name, phone, email, birthDate, role);
        }

        public static OwnerReadResponseDto from(Owner owner){
            return new OwnerReadResponseDto(owner.getMemberName(),
                                            owner.getMemberPhoneNum(),
                                            owner.getEmail(),
                                            owner.getBirthDate(),
                                            owner.getRole());
        }
    }

    @Getter
    @Setter
    @ApiOperation(value ="StaffReadResponseDto")
    public static class StaffReadResponseDto extends MemberReadResponseDto {

        @Builder
        public StaffReadResponseDto(String name, String phone, String email, LocalDate birthDate, Role role) {
            super(name, phone, email, birthDate, role);
        }

        public static OwnerReadResponseDto from(Staff staff){
            return new OwnerReadResponseDto(staff.getMemberName(),
                                            staff.getMemberName(),
                                            staff.getEmail(),
                                            staff.getBirthDate(),
                                            staff.getRole());
        }
    }
}
