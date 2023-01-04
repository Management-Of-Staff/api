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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "week_work_time_id")
    private WeekWorkTime weekWorkTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private Long requestStaffId;

    private Long acceptedStaffId;

    private String acceptCheck;

    private LocalDate pinchDate;

    private LocalDateTime createTime;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String successCheck;
}
