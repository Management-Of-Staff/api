package com.example.sidepot.work.dto;

import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

public class WorkRequestDto {
    @Getter
    public static class createWorkReqDto {
        private Long storeId;
        private Long staffId;
        private LocalTime startTime;
        private LocalTime endTime;
        private Set<DayOfWeek> dayOfWeekList;
    }
    @Getter
    public static class deleteWorkReqDto {
        private Set<Long> workTimeIds;

    }
    @Getter
    public static class updateWorkReqDto {
        private Set<Long> workTimeIds;
        private createWorkReqDto createWorkReqDto;
    }
}
