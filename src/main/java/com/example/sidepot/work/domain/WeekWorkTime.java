package com.example.sidepot.work.domain;

import com.example.sidepot.member.domain.Staff;
import com.example.sidepot.store.domain.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "week_work_time")
@NoArgsConstructor
public class WeekWorkTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "week_work_time_id")
    private Long weekWorkTimeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "day_of_week")
    private String dayOfWeek;

    @Column(name = "start_time")
    private LocalDate startTime;

    @Column(name = "end_time")
    private LocalDate endTime;


    @CreationTimestamp
    @Column(name = "create_time",
            updatable = false,
            nullable = false)
    private LocalDateTime createTime;

}
