package com.example.sidepot.member.dto;

import com.example.sidepot.member.domain.Employment;
import com.example.sidepot.member.domain.Rank;
import com.example.sidepot.member.domain.WorkingStatus;
import com.example.sidepot.work.domain.WeekWorkTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @Getter
    @NoArgsConstructor
    public static class ReadEmploymentResponseDto {

        private Long employmentId;
        private String name;
        private Rank rank;
        private List<ReadWorkTimeWithStaff> readWorkTimsWithStaffList;

        public ReadEmploymentResponseDto(Long employmentId, String name, Rank rank, List<ReadWorkTimeWithStaff> readWorkTimsWithStaffList) {
            this.employmentId = employmentId;
            this.name = name;
            this.rank = rank;
            this.readWorkTimsWithStaffList = readWorkTimsWithStaffList;
        }

        public static ReadEmploymentResponseDto of(Employment employment){
            return new ReadEmploymentResponseDto(employment.getEmploymentId(),
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
