package com.example.sidepot.member.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public class WeekWorkTimeServiceTest {

    private WeekService weekService;

    @BeforeEach
    void setUp(){
        weekService = new WeekService();
    }

    @Test
    void 근무일정추가_한주(){
        final LocalDate startDate = LocalDate.parse("2023-01-01");
        final LocalDate endDate = LocalDate.parse("2023-07-24");
        final LocalTime startTime = LocalTime.parse("12:00");
        final LocalTime endTime = LocalTime.parse("22:00");
        final Set<DayOfWeek> dayOfWeekList = Set.of(DayOfWeek.valueOf("MONDAY, SUNDAY"));
        WWorkAddRequest wWorkAddRequest = new WWorkAddRequest(startDate, endDate, startTime, endTime, dayOfWeekList);
        weekService.addWork(wWorkAddRequest);
    }

    private class WeekService {
        public void addWork(WWorkAddRequest wWorkAddRequest) {

        }
    }

    private class WWorkAddRequest {
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final LocalTime startTime;
        private final LocalTime endTime;
        private final Set<DayOfWeek> dayOfWeekList;
        public WWorkAddRequest(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, Set<DayOfWeek> dayOfWeekList) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.startTime = startTime;
            this.endTime = endTime;
            this.dayOfWeekList = dayOfWeekList;
        }
    }
}
