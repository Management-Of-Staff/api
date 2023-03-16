package com.example.sidepot.work.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

public class WorkTimeRequest {
    @NoArgsConstructor
    @Getter
    public static class WeekWorkAddRequest {
        private LocalTime startTime;
        private LocalTime endTime;
        private Set<DayOfWeek> dayOfWeekList;

    }

    @NoArgsConstructor
    @Getter
    public static class WeekWorkDeleteRequest{
        private Set<Long> weekWorkTimeIds;

    }
    @NoArgsConstructor
    @Getter
    public static class WeekWorkUpdateRequest {
        private List<Long> weekWorkIds;
        private WeekWorkAddRequest weekWorkAddRequest;
    }
}
