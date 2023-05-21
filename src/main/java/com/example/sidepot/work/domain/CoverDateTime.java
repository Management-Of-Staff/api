package com.example.sidepot.work.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CoverDateTime {

    private LocalDate coverDate;
    private LocalTime startTime;
    private LocalTime endTime;

    public CoverDateTime(LocalDate coverDate, LocalTime startTime, LocalTime endTime) {
        this.coverDate = coverDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CoverDateTime that = (CoverDateTime) o;
        return Objects.equals(coverDate, that.coverDate) && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coverDate, startTime, endTime);
    }
}
