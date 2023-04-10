package com.example.sidepot.work.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cover_work_time")
@Entity
public class CoverWorkTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cover_work_time")
    private Long coverWorkTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employment_id")
    private Employment employment;

    @Column(name = "work_time_id")
    private Long workTimeId;

    @Column(name = "requested_staff_id")
    private Long requestedStaffId;

    @Column(name = "accepted_staff_id")
    private Long acceptedStaffId;

    @Column(name = "cover_date")
    private LocalDate coverDate;

    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private DayOfWeek day_of_week;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "success_check")
    private boolean successCheck;

    @Column(name = "cover_work_type")
    @Enumerated(EnumType.STRING)
    private CoverWorkType coverWorkType;

    //요청 생성 시,
    private CoverWorkTime(Long workTimeId, Long requestedStaffId, LocalDate coverDate,
                          LocalDateTime startTime, LocalDateTime endTime) {
        this.workTimeId = workTimeId;
        this.requestedStaffId = requestedStaffId;
        this.coverDate = coverDate;
        this.day_of_week = coverDate.getDayOfWeek();
        this.startTime = startTime;
        this.endTime = endTime;
        this.successCheck = false;
        this.coverWorkType = CoverWorkType.REQUEST;
    }
    //요청 수락 시,
    private CoverWorkTime(Long workTimeId, Long requestedStaffId, Long acceptedStaffId, LocalDate coverDate,
                         LocalDateTime startTime, LocalDateTime endTime) {
        this.workTimeId = workTimeId;
        this.requestedStaffId = requestedStaffId;
        this.acceptedStaffId = acceptedStaffId;
        this.coverDate = coverDate;
        this.day_of_week = coverDate.getDayOfWeek();
        this.startTime = startTime;
        this.endTime = endTime;
        this.successCheck = true;
        this.coverWorkType = CoverWorkType.ACCEPT;
    }
    //요청을 생성할 때,
    public static CoverWorkTime createCoverWorkRequest(Long workTimeId, Long requestStaffId, LocalDate pinchDate,
                                                       LocalDateTime startTime, LocalDateTime endTime){
        return new CoverWorkTime(workTimeId, requestStaffId, pinchDate, startTime, endTime);
    }
    //생성된 요청이 수락될 때,
    public void accomplishCoverWork(Long acceptedStaffId){
        this.acceptedStaffId = acceptedStaffId;
        this.successCheck = true;
    }
    //요청을 수락할 때,
    public static CoverWorkTime createAcceptedCoverWork(Long workTimeId, Long requestStaffId, Long acceptedStaffId,
                                                        LocalDate pinchDate, LocalDateTime startTime, LocalDateTime endTime){
        return new CoverWorkTime(workTimeId, requestStaffId, acceptedStaffId, pinchDate, startTime, endTime);
    }
    public void setEmployment(Employment employment){
        this.employment = employment;
    }
}

