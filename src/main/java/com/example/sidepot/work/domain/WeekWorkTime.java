package com.example.sidepot.work.domain;

import com.example.sidepot.store.domain.AttendanceStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Getter
@Table(name = "week_work_time")
@NoArgsConstructor
public class WeekWorkTime {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "week_work_time_id")
    private Long weekWorkTimeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employment_id")
    private Employment employment;

    @Column(name = "start_time", columnDefinition = "TIME")
    private LocalTime startTime;

    @Column(name = "end_time", columnDefinition = "TIME")
    private LocalTime endTime;

    @Column(name = "day_of_week")
    private DayOfWeek day;



    @Builder
    public WeekWorkTime(Employment employment, DayOfWeek day,
                        LocalTime startTime, LocalTime endTime) {
        this.employment = employment;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static WeekWorkTime createWeekWorkTime(Employment employment, DayOfWeek day,
                                                  LocalTime startTime, LocalTime endTime){
        return new WeekWorkTime(employment, day, startTime, endTime);
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
