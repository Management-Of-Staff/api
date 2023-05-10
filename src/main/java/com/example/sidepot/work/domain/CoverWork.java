package com.example.sidepot.work.domain;


import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.work.dto.CoverWorkRequestDto.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cover_work")
@Entity
public class CoverWork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cover_work_id")
    private Long coverWorkId;

    @Column(name = "working_store_id")
    private Long workingStoreId;

    @ManyToOne
    @JoinColumn(name = "cover_work_notification_id")
    private CoverWorkNotice coverWorkNotice;

    @ManyToOne
    @JoinColumn(name = "work_time_id")
    private WorkTime workTime;

    @Column(name = "working_store_name")
    private String workingStoreName;

    @Column(name = "requested_staff_Name")
    private String requestedStaffName;
    @Column(name = "requested_staff_id")
    private Long requestedStaffId;

    @Column(name = "acceptedStaffId")
    private Long acceptedStaffId;
    @Column(name = "accepted_staff_Name")
    private String acceptedStaffName;

    @Column(name = "cover_date")
    private LocalDate coverDate;

    @Column(name = "day_of_week")
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "success_check")
    private boolean successCheck;

    @Column(name = "is_accepted")
    private boolean isAccepted;

    @Builder
    public CoverWork(Long coverWorkId, Long workingStoreId, CoverWorkNotice coverWorkNotice, WorkTime workTime,
                     String workingStoreName, Long requestedStaffId, Long acceptedStaffId, LocalDate coverDate,
                     DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime,
                     boolean successCheck, boolean isAccepted) {
        this.coverWorkId = coverWorkId;
        this.workingStoreId = workingStoreId;
        this.coverWorkNotice = coverWorkNotice;
        this.workTime = workTime;
        this.workingStoreName = workingStoreName;
        this.requestedStaffId = requestedStaffId;
        this.acceptedStaffId = acceptedStaffId;
        this.coverDate = coverDate;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.successCheck = successCheck;
        this.isAccepted = isAccepted;
    }

    @Builder


    public CoverWork(CreateCoverWorkReqDto createCoverWorkReqDto, WorkTime workTime, CoverWorkNotice coverWorkNotice, LoginMember member) {
        this.requestedStaffName = member.getMemberName();
        this.requestedStaffId = createCoverWorkReqDto.getRequestedStaffId();
        this.coverDate = createCoverWorkReqDto.getCoverDate();
        this.dayOfWeek = createCoverWorkReqDto.getCoverDate().getDayOfWeek();
        this.startTime = workTime.getStartTime();
        this.endTime =  workTime.getEndTime();
        this.successCheck = false;
        this.isAccepted = false;
        this.workTime = workTime;
        this.workingStoreId = createCoverWorkReqDto.getStoreId();
        this.coverWorkNotice = coverWorkNotice;
    }

    public void coverWorkAccepted(LoginMember member){
        this.acceptedStaffName = member.getMemberName();
        this.acceptedStaffId = member.getMemberId();
        this.isAccepted = true;
    }
}

