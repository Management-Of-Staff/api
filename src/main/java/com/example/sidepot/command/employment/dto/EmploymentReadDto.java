package com.example.sidepot.command.employment.dto;

import com.example.sidepot.global.file.BaseFilePath;
import com.example.sidepot.command.member.domain.Rank;
import lombok.*;


public class EmploymentReadDto {
    @Getter
    public static class ReadOneEmploymentResponse {
        private Long employmentId;
        private Long staffId;
        private String name;
        private String phone;
        private BaseFilePath profileImage;
        private Rank rank;
        private Long hourlyWage;
        private Boolean healthCertificate;

        public ReadOneEmploymentResponse(Long employmentId, Long staffId, String name, String phone,
                                         BaseFilePath profileImage, Rank rank, Long hourlyWage, Boolean healthCertificate) {
            this.employmentId = employmentId;
            this.staffId = staffId;
            this.name = name;
            this.phone = phone;
            this.profileImage = profileImage;
            this.rank = rank;
            this.hourlyWage = hourlyWage;
            this.healthCertificate = healthCertificate;
        }
    }
}
