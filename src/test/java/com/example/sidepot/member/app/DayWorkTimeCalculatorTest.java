package com.example.sidepot.member.app;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;


public class DayWorkTimeCalculatorTest {

    final LocalDate startDate = LocalDate.now(); //2023-02-02
    final LocalDate endDate = startDate.plusYears(2); // 2년 동안 주 2일은 208일
    final List<DayOfWeek> dayOfWeekList = List.of(DayOfWeek.SUNDAY, DayOfWeek.THURSDAY);
    List<DDDD> dayWorkTimes = new ArrayList<>();
    int cntTotalDate = 0;

    @Test
    void dateTest() {
        long weeksBetween = ChronoUnit.WEEKS.between(startDate, endDate);
        for(int i = 0; i<weeksBetween ; i++){
            for(DayOfWeek d : dayOfWeekList){
                LocalDate nextWeek = startDate.plusWeeks(i);
                LocalDate nextDay = nextWeek.with(TemporalAdjusters.nextOrSame(d));
                dayWorkTimes.add(new DDDD(nextDay));
                cntTotalDate++;
            }
        }

        for (DDDD d : dayWorkTimes){
            System.out.println(d.getWorkDate());
        }

        Assertions.assertThat(cntTotalDate).isEqualTo((int)(365 * 2 / 7 * 2 + 0.25d));
    }


    public static class DDDD{
        private LocalDate workDate;

        public DDDD(LocalDate workDate) {
            this.workDate = workDate;
        }

        public LocalDate getWorkDate() {
            return workDate;
        }
    }
}
