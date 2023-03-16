package com.example.sidepot.work.dto;

import com.example.sidepot.member.domain.Staff;
import lombok.Builder;
import lombok.Getter;

public class EmploymentAddDto {

    @Getter
    public static class FindStaffToInviteRequest {
        private String phoneNum;
    }

    @Getter
    public static class FindStaffToInviteResponse {
        private Long staffId;
        private String phone;
        private String name;


        private FindStaffToInviteResponse(Long staffId, String phone, String name) {
            this.staffId = staffId;
            this.phone = phone;
            this.name = name;
        }

        public static FindStaffToInviteResponse of(Staff staff){
            return new FindStaffToInviteResponse(staff.getMemberId(),
                                                 staff.getMemberPhoneNum(),
                                                 staff.getMemberName());

        }
    }
}
