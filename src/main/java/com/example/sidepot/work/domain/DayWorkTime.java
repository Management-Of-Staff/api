package com.example.sidepot.work.domain;

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
    private Long id;

    private Long staffId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "week_work_time_id")
    private WeekWorkTime weekWorkTime;

    private LocalDate workDate;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String day;

    private String attendanceCheck;
}
