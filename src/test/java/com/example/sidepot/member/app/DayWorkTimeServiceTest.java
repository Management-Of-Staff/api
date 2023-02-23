package com.example.sidepot.member.app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.LongStream;


public class DayWorkTimeServiceTest {

    private DayWorkTimeService dayWorkTimeService;

    @BeforeEach
    void setUp(){
        dayWorkTimeService = new DayWorkTimeService();
    }

    @Test
    void 근무일정_추가전에_날짜생성기(){
        final String startDate = "1999-01-01";
        final String endDate = "2002-06-23";
        final String startTime = "12:00";
        final String endTime = "22:00";
        final Set<String> daysOfWeek= Set.of("MONDAY","SUNDAY");
        final DayWorkTimeAddRequest dayWorkTimeAddRequest = new DayWorkTimeAddRequest(startDate,endDate,startTime,endTime,daysOfWeek);
        dayWorkTimeService.addDate(dayWorkTimeAddRequest);

    }

    private class DayWorkTimeService {
        public void addDate(DayWorkTimeAddRequest dayWorkTimeAddRequest) {
            LongAdder count = new LongAdder();
            List<DayWorkTime> dayWorkTimes = dayWorkTimeAddRequest.dayOfWeekList.stream()
                    .flatMap(day -> LongStream.range(0, ChronoUnit.WEEKS.between(dayWorkTimeAddRequest.startDate, dayWorkTimeAddRequest.endDate))
                            .mapToObj(i -> {
                                count.increment();
                                LocalDate nextWeek = dayWorkTimeAddRequest.startDate.plusWeeks(i);
                                LocalDate nextWorkDate = nextWeek.with(TemporalAdjusters.nextOrSame(day));
                                return new DayWorkTime(count.longValue(), nextWorkDate, dayWorkTimeAddRequest.startTime, dayWorkTimeAddRequest.endTime, day, false);
                            }))
                    .collect(Collectors.toList());
            dayWorkTimes.stream().forEach(i -> System.out.println(i.getWorkDate()));
        }
    }

    private class DayWorkTime {
        private final Long id;
        private final LocalDate workDate;
        private final LocalTime startTime;
        private final LocalTime endTime;
        private final DayOfWeek dayOfWeek;
        private final Boolean attendanceCheck;


        public DayWorkTime(Long id, LocalDate workDate, LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek, Boolean attendanceCheck) {
            this.id = id;
            this.workDate = workDate;
            this.startTime = startTime;
            this.endTime = endTime;
            this.dayOfWeek = dayOfWeek;
            this.attendanceCheck = attendanceCheck;
        }
        public LocalDate getWorkDate() {
            return workDate;
        }
    }

    private static class DayWorkTimeAddRequestWithString {

        private final LocalDate startDate;
        private final LocalDate endDate;
        private final LocalTime startTime;
        private final LocalTime endTime;

        public DayWorkTimeAddRequestWithString(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.startTime = startTime;
            this.endTime = endTime;
        }
    }
    private static class DayWorkTimeAddRequest {
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final LocalTime startTime;
        private final LocalTime endTime;
        private final Set<DayOfWeek> dayOfWeekList;
        public DayWorkTimeAddRequest(String startDate, String endDate, String startTime, String endTime, Set<String> dayOfWeekList) {
            this.startDate = LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE);
            this.endDate = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);
            this.startTime = LocalTime.parse(endTime);
            this.endTime = LocalTime.parse(endTime);
            this.dayOfWeekList = dayOfWeekList.stream().map(DayOfWeek::valueOf).collect(Collectors.toSet());
        }

    }
}
