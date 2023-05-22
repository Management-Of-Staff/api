package com.example.sidepot.work.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StaffWorkScheduleResDto {
    private Long workTimeId;
    private Long staffId;
    private String staffName;
    private Long storeId;
    private String branchName;
    private String storeName;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<CoverSchedule> coverScheduleList;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CoverSchedule{
        private Long coverWorkId;
        private Long requestedStaffId;
        private String requestedStaffName;
        private LocalDate coverDate;
        private LocalTime startTime;
        private LocalTime endTime;
    }
}
