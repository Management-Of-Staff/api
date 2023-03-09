package com.example.sidepot.work.domain.refactor.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class WorkContract {

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "start_ime")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "total_time")
    private Long totalTime;

    @Column(name = "work_period")
    private Long workPeriod;

    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    public WorkContract(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime,
                        Long totalTime, Long workPeriod, EmploymentType employmentType) {
        verifyStartDate(startDate);
        verifyEndDate(endDate);
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalTime = totalTime;
        this.workPeriod = workPeriod;
        this.employmentType = employmentType;
    }

    private void verifyStartDate(LocalDate startDate){this.startDate = startDate != null ? startDate : LocalDate.now();}

    private void verifyEndDate(LocalDate endDate){
        this.endDate = startDate != null ? endDate : LocalDate.now();
    }

    public static WorkContract from(LocalDate startDate, LocalDate endDate,
                                    LocalTime startTime, LocalTime endTime,
                                    Long totalTime, Long workPeriod, EmploymentType employmentType){
        return new WorkContract(startDate, endDate, startTime, endTime, totalTime, workPeriod, employmentType);
    }
}
