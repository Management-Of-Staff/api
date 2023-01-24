package com.example.sidepot.work.domain;

import com.example.sidepot.store.domain.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "pinch_time")
@NoArgsConstructor
public class PinchTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pinch_time_id")
    private Long pinchTimeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "week_work_time_id")
    private WeekWorkTime weekWorkTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "request_staff_id")
    private Long requestStaffId;

    @Column(name = "accepted_staff_id")
    private Long acceptedStaffId;

    @Column(name = "accept_check")
    private String acceptCheck;

    @Column(name = "pinch_date")
    private LocalDate pinchDate;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "success_check")
    private String successCheck;
}
