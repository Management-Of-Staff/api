package com.example.sidepot.work.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@Table(name = "pinch_time")
@Entity
public class PinchTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pinch_time_id")
    private Long pinchTimeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_time_id")
    private WorkTime workTime;

    @Column(name = "request_staff_id")
    private Long requestStaffId;

    @Column(name = "accepted_staff_id")
    private Long acceptedStaffId;

    @Column(name = "pinch_date")
    private LocalDate pinchDate;

    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private DayOfWeek day_of_week;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "success_check")
    private boolean successCheck;

    public PinchTime(WorkTime workTime, Long requestStaffId, Long acceptedStaffId, LocalDate pinchDate,
                     LocalDateTime createTime, LocalDateTime startTime, LocalDateTime endTime) {
        this.workTime = workTime;
        this.requestStaffId = requestStaffId;
        this.acceptedStaffId = acceptedStaffId;
        this.pinchDate = pinchDate;
        this.day_of_week = pinchDate.getDayOfWeek();
        this.createTime = createTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.successCheck = false;
    }

    //요청 수락 시 생성되는 경우,
    public static PinchTime createPinchTime(WorkTime workTime, Long requestStaffId, LocalDate pinchDate,
                                            LocalDateTime createTime, LocalDateTime startTime, LocalDateTime endTime){
        return new PinchTime(workTime, requestStaffId, requestStaffId, pinchDate, createTime, startTime ,endTime);
    }
}

