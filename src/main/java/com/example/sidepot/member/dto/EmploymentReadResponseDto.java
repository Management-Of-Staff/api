package com.example.sidepot.member.dto;

import com.example.sidepot.member.domain.Employment;
import com.example.sidepot.member.domain.Rank;
import com.example.sidepot.member.domain.WorkingStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

public class EmploymentReadResponseDto {
    @Data
    @NoArgsConstructor
    public static class ReadEmploymentListResponseDto {

        private Long EmploymentId;
        private String staffName;
        private WorkingStatus workingStatus;

        public ReadEmploymentListResponseDto(Long employmentId, String staffName, WorkingStatus workingStatus) {
            this.EmploymentId = employmentId;
            this.staffName = staffName;
            this.workingStatus = workingStatus;
        }

        public static ReadEmploymentListResponseDto of(Employment employment){
            return new ReadEmploymentListResponseDto(employment.getEmploymentId(),
                                                     employment.getStaff().getName(),
                                                     employment.getWorkingStatus());
        }
    }

    @Data
    @NoArgsConstructor
    public static class ReadEmploymentResponseDto {

        private Long employmentId;
        private String name;
        private Rank rank;

        public ReadEmploymentResponseDto(Long employmentId, String name, Rank rank) {
            this.employmentId = employmentId;
            this.name = name;
            this.rank = rank;
        }

        public static ReadEmploymentResponseDto of(Employment employment){
            return new ReadEmploymentResponseDto(employment.getEmploymentId(),
                                                 employment.getStaffName(),
                                                 employment.getRank());
        }
    }

}
