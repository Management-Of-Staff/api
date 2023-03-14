package com.example.sidepot.work.dto;

import com.example.sidepot.work.domain.Employment;
import com.example.sidepot.member.domain.Rank;
import com.example.sidepot.work.domain.WeekWorkTime;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class EmploymentReadDto {
    @Getter
    public static class ReadEmploymentListResponse {
        private Long employmentId;
        private Long staffId;
        private String staffName;
        private String profilePath;
        private Boolean healthCertificateCheck;
        private List<ReadWorkTimeWithStaff> workTimeRequests;


        public ReadEmploymentListResponse(Long employmentId, Long staffId, String staffName, String profilePath,
                                          Boolean healthCertificateCheck, List<ReadWorkTimeWithStaff> workTimeRequests) {
            this.employmentId = employmentId;
            this.staffId = staffId;
            this.staffName = staffName;
            this.profilePath = profilePath;
            this.healthCertificateCheck = healthCertificateCheck;
            this.workTimeRequests = workTimeRequests;
        }

        public static ReadEmploymentListResponse of(Employment employment){
            return new ReadEmploymentListResponse(employment.getEmploymentId(),
                                                     employment.getStaff().getMemberId(),
                                                     employment.getStaff().getMemberName(),
                                                     employment.getStaff().getProfileImage().getFileSavePath(),
                                                     false,
                                                     employment.getWeekWorkTimeList().stream().map(list -> new ReadWorkTimeWithStaff(list)).collect(Collectors.toList()));
        }
    }

    @Getter
    public static class ReadOneEmploymentResponse {
        private Long employmentId;
        private Long staffId;
        private String name;
        private String phone;
        private String profilePath;
        private Rank rank;
        private Long hourlyWage;
        private List<ReadWorkTimeWithStaff> readWorkTimesWithStaffList;

        @Builder
        public ReadOneEmploymentResponse(Long employmentId, Long staffId, String name, String phone, String profilePath, Rank rank,
                                         Long hourlyWage, List<ReadWorkTimeWithStaff> readWorkTimesWithStaffList) {
            this.employmentId = employmentId;
            this.staffId = staffId;
            this.name = name;
            this.phone = phone;
            this.profilePath = profilePath;
            this.rank = rank;
            this.hourlyWage = hourlyWage;
            this.readWorkTimesWithStaffList = readWorkTimesWithStaffList;
        }

        public static ReadOneEmploymentResponse of(Employment employment){
            return ReadOneEmploymentResponse.builder()
                    .employmentId(employment.getEmploymentId())
                    .staffId(employment.getStaff().getMemberId())
                    .name(employment.getStaff().getMemberName())
                    .phone(employment.getStaff().getMemberPhoneNum())
                    .hourlyWage(employment.getHourlyWage())
                    .rank(employment.getRank())
                    .readWorkTimesWithStaffList(employment.getWeekWorkTimeList().stream()
                            .map(ReadWorkTimeWithStaff::new)
                            .collect(Collectors.toList()))
                    .build();
        }
    }

    @Getter
    public static class ReadWorkTimeWithStaff{
        private DayOfWeek dayOfWeek;
        private LocalTime startTime;
        private LocalTime endTime;
        public ReadWorkTimeWithStaff(WeekWorkTime weekWorkTime) {
            this.dayOfWeek = weekWorkTime.getDay();
            this.startTime = weekWorkTime.getStartTime();
            this.endTime = weekWorkTime.getEndTime();
        }
    }
}
