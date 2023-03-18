package com.example.sidepot.work.dto;

import com.example.sidepot.global.file.BaseFilePath;
import com.example.sidepot.work.domain.Employment;
import com.example.sidepot.member.domain.Rank;
import com.example.sidepot.work.domain.WorkTime;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

public class EmploymentReadDto {

    @Getter
    public static class ReadEmploymentListResponse {
        private Long employmentId;
        private Long staffId;
        private String staffName;
        private BaseFilePath profileImage;
        private Boolean healthCertificateCheck;
        private List<ReadWorkTime> workTimeRequests;

        public ReadEmploymentListResponse(Long employmentId, Long staffId, String staffName, BaseFilePath profileImage,
                                          Boolean healthCertificateCheck, List<ReadWorkTime> workTimeRequests) {
            this.employmentId = employmentId;
            this.staffId = staffId;
            this.staffName = staffName;
            this.profileImage = profileImage;
            this.healthCertificateCheck = healthCertificateCheck;
            this.workTimeRequests = workTimeRequests;
        }

        public static ReadEmploymentListResponse of(Employment employment){
            return new ReadEmploymentListResponse(employment.getEmploymentId(),
                                                     employment.getStaff().getMemberId(),
                                                     employment.getStaff().getMemberName(),
                                                     employment.getStaff().getProfileImage(),
                                                     false,
                                                     employment.getWorkTimeList().stream()
                                                             .map(ReadWorkTime::of)
                                                             .collect(Collectors.toList()));
        }
    }

    @Getter
    public static class ReadOneEmploymentResponse {
        private Long employmentId;
        private Long staffId;
        private String name;
        private String phone;
        private BaseFilePath profileImage;
        private Rank rank;
        private Long hourlyWage;
        private List<ReadWorkTime> readWorkTimesWithStaffList;

        public ReadOneEmploymentResponse(Long employmentId, Long staffId, String name, String phone, BaseFilePath profileImage, Rank rank,
                                         Long hourlyWage, List<ReadWorkTime> readWorkTimesWithStaffList) {
            this.employmentId = employmentId;
            this.staffId = staffId;
            this.name = name;
            this.phone = phone;
            this.profileImage = profileImage;
            this.rank = rank;
            this.hourlyWage = hourlyWage;
            this.readWorkTimesWithStaffList = readWorkTimesWithStaffList;
        }

        public static ReadOneEmploymentResponse of(Employment employment){
            return new ReadOneEmploymentResponse(
                                employment.getEmploymentId(),
                                employment.getStaff().getMemberId(),
                                employment.getStaff().getMemberName(),
                                employment.getStaff().getMemberPhoneNum(),
                                employment.getStaff().getProfileImage(),
                                employment.getRank(), employment.getHourlyWage(),
                                employment.getWorkTimeList().stream()
                                    .map(ReadWorkTime::of)
                                    .collect(Collectors.toList()));
        }
    }

    @Getter
    public static class ReadWorkTime {
        private Long workTimeId;
        private DayOfWeek dayOfWeek;
        private LocalTime startTime;
        private LocalTime endTime;

        private ReadWorkTime(Long workTimeId, DayOfWeek dayOfWeek,
                             LocalTime startTime, LocalTime endTime) {
            this.workTimeId = workTimeId;
            this.dayOfWeek = dayOfWeek;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public static ReadWorkTime of(WorkTime workTime) {
            return new ReadWorkTime(workTime.getWorkTimeId(), workTime.getDay(),
                                             workTime.getStartTime(), workTime.getEndTime());
        }
    }
}
