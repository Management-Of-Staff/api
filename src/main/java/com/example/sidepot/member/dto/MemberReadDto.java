package com.example.sidepot.member.dto;

import com.example.sidepot.member.domain.Member;
import com.example.sidepot.member.domain.Owner;
import com.example.sidepot.member.domain.Role;
import com.example.sidepot.member.domain.Staff;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;


public class MemberReadDto {
    @Getter
    public static class MemberReadResponseDto{
        private String name;
        private String phone;
        private String email;
        private LocalDate birthDate;
        private Role role;
        private String profilePath;

        public MemberReadResponseDto(String name, String phone, String email,
                                     LocalDate birthDate, Role role, String profilePath) {
            this.name = name;
            this.phone = phone;
            this.email = email;
            this.birthDate = birthDate;
            this.role = role;
            this.profilePath = profilePath;
        }

        public static MemberReadResponseDto of(Member member){
            return new MemberReadResponseDto(member.getMemberName(),
                                             member.getMemberPhoneNum(),
                                             member.getEmail(),
                                             member.getBirthDate(),
                                             member.getRole(),
                                             member.getProfileImage().getFileSavePath());
        }
    }
    @Getter
    public static class OwnerReadResponseDto extends MemberReadResponseDto {

        @Builder
        public OwnerReadResponseDto(String name, String phone, String email,
                                    LocalDate birthDate, Role role, String profilePath) {
            super(name, phone, email, birthDate, role, profilePath);
        }

        public static OwnerReadResponseDto of(Owner owner){
            return new OwnerReadResponseDto(owner.getMemberName(),
                                            owner.getMemberPhoneNum(),
                                            owner.getEmail(),
                                            owner.getBirthDate(),
                                            owner.getRole(),
                                            owner.getProfileImage().getFileSavePath());
        }
    }

    @Getter
    public static class StaffReadResponseDto extends MemberReadResponseDto {

        @Builder
        public StaffReadResponseDto(String name, String phone, String email,
                                    LocalDate birthDate, Role role, String profilePath) {
            super(name, phone, email, birthDate, role, profilePath);
        }

        public static StaffReadResponseDto of(Staff staff){
            return new StaffReadResponseDto(staff.getMemberName(),
                                            staff.getMemberName(),
                                            staff.getEmail(),
                                            staff.getBirthDate(),
                                            staff.getRole(),
                                            staff.getProfileImage().getFileSavePath());
        }
    }
}
