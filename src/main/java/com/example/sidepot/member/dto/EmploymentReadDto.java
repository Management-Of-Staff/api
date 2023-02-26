package com.example.sidepot.member.dto;

import com.example.sidepot.member.domain.Employment;
import com.example.sidepot.member.domain.Rank;
import com.example.sidepot.member.domain.WorkingStatus;
import com.example.sidepot.work.domain.WeekWorkTime;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EmploymentReadDto {
    @Data
    @NoArgsConstructor
    public static class ReadEmploymentListResponse {

        private Long EmploymentId;
        private String staffName;
        private WorkingStatus workingStatus;
        private String profilePath;
        private Boolean healthCertificateCheck;
        private List<ReadWorkTimeWithStaff> workTimeRequests;

        @Builder
        public ReadEmploymentListResponse(Long employmentId, String staffName, WorkingStatus workingStatus, String profilePath,
                                          Boolean healthCertificateCheck, List<ReadWorkTimeWithStaff> workTimeRequests) {
            EmploymentId = employmentId;
            this.staffName = staffName;
            this.workingStatus = workingStatus;
            this.profilePath = profilePath;
            this.healthCertificateCheck = healthCertificateCheck;
            this.workTimeRequests = workTimeRequests;
        }

        public static ReadEmploymentListResponse of(Employment employment){
            return new ReadEmploymentListResponse(employment.getEmploymentId(),
                                                     employment.getStaff().getName(),
                                                     employment.getWorkingStatus(),
                                                     null,
                                                     false,
                                                     employment.getWeekWorkTimeList().stream().map(list -> new ReadWorkTimeWithStaff(list)).collect(Collectors.toList()));
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ReadOneEmploymentResponse {

        private Long employmentId;
        private String name;
        private String phone;
        private String profilePath;
        private Rank rank;
        private Long hourlyWage;
        private List<ReadWorkTimeWithStaff> readWorkTimsWithStaffList;

        public ReadOneEmploymentResponse(Long employmentId, String name, Rank rank, List<ReadWorkTimeWithStaff> readWorkTimsWithStaffList) {
            this.employmentId = employmentId;
            this.name = name;
            this.rank = rank;
            this.readWorkTimsWithStaffList = readWorkTimsWithStaffList;
        }

        public static ReadOneEmploymentResponse of(Employment employment){
            return new ReadOneEmploymentResponse(employment.getEmploymentId(),
                                                 employment.getStaffName(),
                                                 employment.getRank(),
                                                 employment.getWeekWorkTimeList().stream().map(ReadWorkTimeWithStaff::new).collect(Collectors.toList()));
        }
    }

    @Getter
    @NoArgsConstructor

    public static class ReadWorkTimeWithStaff{
        private Set<DayOfWeek> dayOfWeekList;
        private LocalDate startDate;
        private LocalDate endDate;
        private LocalTime startTime;
        private LocalTime endTime;
        public ReadWorkTimeWithStaff(WeekWorkTime weekWorkTime) {
            this.dayOfWeekList = weekWorkTime.getDay();
            this.startDate = weekWorkTime.getStartDate();
            this.endDate = weekWorkTime.getEndDate();
            this.startTime = weekWorkTime.getStartTime();
            this.endTime = weekWorkTime.getEndTime();
        }
    }
}
