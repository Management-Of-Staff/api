package com.example.sidepot.command.work.domain;

import com.example.sidepot.command.attendance.domain.AttendanceStatus;
import com.example.sidepot.command.member.domain.Staff;
import com.example.sidepot.command.work.dto.CoverWorkRequestDto.CreateCoverWorkReqDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cover_work")
@Entity
public class CoverWork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cover_work_id")
    private Long coverWorkId;
    @Embedded
    private RequestedStaff requestedStaff;
    @Embedded
    private AcceptedStaff acceptedStaff;
    @Embedded
    private CoverDateTime coverDateTime;
    @Embedded
    private WorkTimeId workTime;
    @Column(name = "is_accepted")
    private Boolean isAccepted;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_manager_id")
    private CoverManager coverManager;

    public CoverWork(CoverDateTime coverDateTime, WorkTimeId workTimeId, RequestedStaff requestedStaff) {
        this.coverDateTime = coverDateTime;
        this.workTime = workTimeId;
        this.requestedStaff = requestedStaff;
        this.isAccepted = false;
    }

    public static CoverWork newCoverWork(Staff requestedStaff, CreateCoverWorkReqDto createCwReqDto, WorkTime wtPs){
        return new CoverWork(
                new CoverDateTime(createCwReqDto.getCoverDate(), wtPs.getStartTime(), wtPs.getEndTime()),
                new WorkTimeId(wtPs.getWorkTimeId()),
                new RequestedStaff(requestedStaff.getMemberId(), requestedStaff.getMemberName()));
    }

    public CoverWork setCoverManager(CoverManager coverManager){
        this.coverManager = coverManager;
        return this;
    }

    public void cancel(){
        this.isAccepted = false;
        this.acceptedStaff = null;
    }

    public void acceptCover(AcceptedStaff acceptedStaff) {
        this.acceptedStaff = acceptedStaff;
        this.isAccepted = true;
    }

    public boolean isOverlapped(CoverDateTime coverDateTime) {
        return  ((this.coverDateTime.getCoverDate().equals(coverDateTime.getCoverDate()))
                && (this.coverDateTime.getStartTime().isBefore(coverDateTime.getEndTime()))
                && (this.coverDateTime.getEndTime().isAfter(coverDateTime.getStartTime())));

    }


    public void isNotWorkStartTime(LocalDateTime now){
        isWorkingDay(now);
        if (this.coverDateTime.getStartTime().isBefore(now.toLocalTime().minusMinutes(10))){
            throw new IllegalStateException("출근시간이 아닙니다");
        }
    }

    public void isNotWorkOffTime(LocalDateTime now, Long allowedLeaveTime){
        isWorkingDay(now);
        if (this.getCoverDateTime().getEndTime().isBefore(now.toLocalTime().minusMinutes(allowedLeaveTime))){
            throw new IllegalStateException("아칙 퇴근 시간이 아닙니다.");
        }
    }

    public AttendanceStatus checkIn(LocalDateTime now, Long allowedLateTime) {
        isPossibleCheckIn(now);
        if (now.toLocalTime().isAfter(this.coverDateTime.getStartTime().minusMinutes(10))
                && now.toLocalTime().isBefore(this.coverDateTime.getStartTime().plusMinutes(10))) {
            return AttendanceStatus.CHECK_IN;
        }

        if(now.toLocalTime().isAfter(this.coverDateTime.getStartTime())
                && now.toLocalTime().isBefore(this.coverDateTime.getStartTime().plusMinutes(allowedLateTime))){
            return AttendanceStatus.LATE;
        }

        if(now.toLocalTime().isAfter(this.coverDateTime.getStartTime().plusMinutes(allowedLateTime))){
            return AttendanceStatus.ABSENCE;
        }
        return AttendanceStatus.ABSENCE;
    }

    public void isPossibleCheckIn(LocalDateTime now){
        isWorkingDay(now);
        if(now.toLocalTime().isAfter(this.getCoverDateTime().getStartTime().minusMinutes(10))
                && now.toLocalTime().isBefore(this.getCoverDateTime().getStartTime().plusMinutes(10))){
            throw new IllegalStateException("아직 출근 시간이 아닙니다");
        }
    }

    private void isWorkingDay(LocalDateTime now) {
        if(this.coverDateTime.getCoverDate().equals(now.toLocalDate())){
            throw new IllegalStateException("근무 날짜가 아닙니다.");
        }
    }
}

