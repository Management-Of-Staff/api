package com.example.sidepot.work.domain;

import com.example.sidepot.global.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Getter
@Table(name = "work_time")
@NoArgsConstructor
public class WorkTime extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "work_time_id")
    private Long workTimeId;

    @ManyToOne(fetch = FetchType.LAZY)
    //@JsonIgnoreProperties("workTimeList")
    @JoinColumn(name = "employment_id")
    private Employment employment;

    @Column(name = "start_time", columnDefinition = "TIME")
    private LocalTime startTime;

    @Column(name = "end_time", columnDefinition = "TIME")
    private LocalTime endTime;

    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private DayOfWeek day;

    public WorkTime(DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static WorkTime createWorkTime(DayOfWeek day, LocalTime startTime, LocalTime endTime){
        return new WorkTime(day, startTime, endTime);
    }

    public void remove() {
        this.employment = null;
    }

    public void setEmployment(Employment employment) {
        this.employment = employment;
    }

    /**
     * 근무 요일 조회
     * ex) '월, 화, 수'
     */
    public String getWorkDays() {
        // 예외 예정
//        if(day == null || day.isEmpty()) {
//            return "";
//        }
//        return day.stream()
//                .map(Enum::name)
//                .collect(Collectors.joining(","));
        return null;
    }
}
