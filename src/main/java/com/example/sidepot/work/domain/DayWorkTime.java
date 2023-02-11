package com.example.sidepot.work.domain;

import com.example.sidepot.member.domain.Employment;
import com.example.sidepot.member.domain.Staff;
import com.example.sidepot.store.domain.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Table(name = "day_work_time")
@NoArgsConstructor
public class DayWorkTime {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "sequence",
            parameters = {
                    @Parameter(name = SequenceStyleGenerator.SEQUENCE_PARAM, value = SequenceStyleGenerator.DEF_SEQUENCE_NAME),
                    @Parameter(name = SequenceStyleGenerator.INITIAL_PARAM, value = "1"),
                    @Parameter(name = SequenceStyleGenerator.INCREMENT_PARAM, value = "2000"),
                    @Parameter(name = AvailableSettings.PREFERRED_POOLED_OPTIMIZER, value = "pooled-lo")})
    @Column(name = "day_work_time_id")
    private Long dayWorkTimeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employment_id")
    private Employment employment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "week_work_time_id")
    private WeekWorkTime weekWorkTime;

    @Column(name = "work_date")
    private LocalDate workDate;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "day_of_week")
    private String dayOfWeek;

    @Column(name = "attendance_check")
    private String attendanceCheck;

    public DayWorkTime(Employment employment, WeekWorkTime weekWorkTime, LocalDate workDate, LocalDateTime startTime,
                       LocalDateTime endTime, String dayOfWeek, String attendanceCheck) {
        this.employment = employment;
        this.weekWorkTime = weekWorkTime;
        this.workDate = workDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dayOfWeek = dayOfWeek;
        this.attendanceCheck = attendanceCheck;
    }

    public void createTestSet(){
        this.startTime = LocalDateTime.now();
    }

    public static DayWorkTime create(){
        return new DayWorkTime();
    }
}
