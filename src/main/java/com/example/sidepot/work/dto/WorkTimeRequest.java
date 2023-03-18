package com.example.sidepot.work.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public class WorkTimeRequest {
    @Getter
    public static class WorkAddRequest {
        private Long staffId;
        private LocalTime startTime;
        private LocalTime endTime;
        private Set<DayOfWeek> dayOfWeekList;

    }
    @Getter
    public static class WorkDeleteRequest {
        private Set<Long> workTimeIds;

    }
    @Getter
    public static class WorkUpdateRequest {
        private Set<Long> workTimeIds;
        private WorkAddRequest workAddRequest;
    }
}
