package com.example.sidepot.member.dto;

import com.example.sidepot.global.file.BaseFilePath;
import com.example.sidepot.member.domain.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


public class MemberReadDto {
    @Getter
    public static class MemberReadResponseDto{
        private String name;
        private String phone;
        private String email;
        private LocalDate birthDate;
        private Role role;
        private BaseFilePath profileImage;

        public MemberReadResponseDto(String name, String phone, String email,
                                     LocalDate birthDate, Role role, BaseFilePath profileImage) {
            this.name = name;
            this.phone = phone;
            this.email = email;
            this.birthDate = birthDate;
            this.role = role;
            this.profileImage = profileImage;
        }

        public static MemberReadResponseDto of(Member member){
            return new MemberReadResponseDto(member.getMemberName(),
                                             member.getMemberPhoneNum(),
                                             member.getEmail(),
                                             member.getBirthDate(),
                                             member.getRole(),
                                             member.getProfileImage());
        }
    }
    @Getter
    public static class OwnerReadResponseDto extends MemberReadResponseDto {

        @Builder
        public OwnerReadResponseDto(String name, String phone, String email,
                                    LocalDate birthDate, Role role, BaseFilePath profileImage) {
            super(name, phone, email, birthDate, role, profileImage);
        }

        public static OwnerReadResponseDto of(Owner owner){
            return new OwnerReadResponseDto(owner.getMemberName(),
                                            owner.getMemberPhoneNum(),
                                            owner.getEmail(),
                                            owner.getBirthDate(),
                                            owner.getRole(),
                                            owner.getProfileImage());
        }
    }

    @Getter
    public static class StaffReadResponseDto extends MemberReadResponseDto {

        @Builder
        public StaffReadResponseDto(String name, String phone, String email,
                                    LocalDate birthDate, Role role, BaseFilePath profileImage) {
            super(name, phone, email, birthDate, role, profileImage);
        }

        public static StaffReadResponseDto of(Staff staff){
            return new StaffReadResponseDto(staff.getMemberName(),
                                            staff.getMemberName(),
                                            staff.getEmail(),
                                            staff.getBirthDate(),
                                            staff.getRole(),
                                            staff.getProfileImage());
        }
    }
    @NoArgsConstructor
    @Getter
    public static class StaffSearchRequestDto {
        private String phoneNum;
    }

    @NoArgsConstructor
    @Getter
    public static class StaffSearchResponseDto {
        private Long staffId;
        private String memberPhoneNum;
        private String memberName;
        private BaseFilePath profileImage;

        public StaffSearchResponseDto(Long staffId, String memberPhoneNum, String memberName, BaseFilePath profileImage) {
            this.staffId = staffId;
            this.memberPhoneNum = memberPhoneNum;
            this.memberName = memberName;
            this.profileImage = profileImage;
        }

        public static StaffSearchResponseDto of(Staff staff){
            return new StaffSearchResponseDto(
                    staff.getMemberId(),
                    staff.getMemberPhoneNum(),
                    staff.getMemberName(),
                    staff.getProfileImage());
        }
    }
}
