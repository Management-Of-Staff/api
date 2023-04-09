package com.example.sidepot.work.dao;

import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Getter
public class StaffWorkOnDay {
    private Long employmentId;
    private String staffName;
    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;

    public StaffWorkOnDay(Long employmentId, String staffName, DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        this.employmentId = employmentId;
        this.staffName = staffName;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static class ReadPinchWork{
        private Long pinchTimeId;
        private String acceptStaffName;
        private String requestStaffName;
    }
}
