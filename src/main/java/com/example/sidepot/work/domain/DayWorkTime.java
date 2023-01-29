package com.example.sidepot.work.domain;

import com.example.sidepot.member.domain.Staff;
import com.example.sidepot.store.domain.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "day_work_time")
@NoArgsConstructor
public class DayWorkTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "day_work_time_id")
    private Long dayWorkTimeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

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
}
