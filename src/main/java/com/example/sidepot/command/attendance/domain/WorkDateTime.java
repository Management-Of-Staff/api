package com.example.sidepot.command.attendance.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@NoArgsConstructor
@Embeddable
public class WorkDateTime {

    private LocalTime startTime;
    private LocalTime endTime;
    private DayOfWeek dayOfWeek;
    private LocalDate workDate;

    public WorkDateTime(LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek, LocalDate workDate) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
        this.workDate = workDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkDateTime that = (WorkDateTime) o;
        return Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && dayOfWeek == that.dayOfWeek && Objects.equals(workDate, that.workDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, dayOfWeek, workDate);
    }
}
