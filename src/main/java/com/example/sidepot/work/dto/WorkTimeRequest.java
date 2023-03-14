package com.example.sidepot.work.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public class WorkTimeRequest {
    @Getter
    @NoArgsConstructor
    public static class WeekWorkAddRequest {
        private LocalDate startDate;
        private LocalDate endDate;
        private LocalTime startTime;
        private LocalTime endTime;
        private Set<DayOfWeek> dayOfWeekList;

        public WeekWorkAddRequest(LocalDate startDate, LocalDate endDate,
                                  LocalTime startTime, LocalTime endTime, Set<DayOfWeek> dayOfWeekList) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.startTime = startTime;
            this.endTime = endTime;
            this.dayOfWeekList = dayOfWeekList;
        }
    }

    @Getter
    public static class WeekWorkDeleteRequest{
        private Long storeId;
        private Long staffId;
        private Long weekWorkTimeId;

        public WeekWorkDeleteRequest(Long storeId, Long staffId, Long weekWorkTimeId) {
            this.storeId = storeId;
            this.staffId = staffId;
            this.weekWorkTimeId = weekWorkTimeId;
        }
    }


}
