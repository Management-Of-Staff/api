package com.example.sidepot.command.attendance.domain;

import com.example.sidepot.command.work.domain.CoverWork;
import com.example.sidepot.command.work.domain.StoreInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cover_attendance")
@Entity
public class CoverAttendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private StoreInfo storeInfo;

    @Embedded
    private WorkerId workerId;

    @Embedded
    private WorkDateTime workDateTime;

    @Column(name = "cover_work_id")
    private Long coverWorkId;

    @Column(name = "check_in_time")
    private LocalDateTime checkInTime;

    @Column(name = "check_out_time")
    private LocalDateTime checkOutTime;

    @Column(name = "attendance_status")
    @Enumerated(EnumType.STRING)
    private AttendanceStatus attendanceStatus;

    @Column(name = "realWorkTime")
    private LocalTime realWorkTime;

    public CoverAttendance(StoreInfo storeInfo, WorkerId workerId,
                           LocalDateTime checkInTime, AttendanceStatus attendanceStatus) {
        this.storeInfo = storeInfo;
        this.workerId = workerId;
        this.checkInTime = checkInTime;
        this.attendanceStatus = attendanceStatus;
        //Events.raise(출근 알림);
        //Events.raise(고용 근무 상태 변경)
    }

    public CoverAttendance(StoreInfo storeInfo, WorkerId workerId, WorkDateTime workDateTime,
                           Long coverWorkId, AttendanceStatus attendanceStatus) {
        this.storeInfo = storeInfo;
        this.workerId = workerId;
        this.workDateTime = workDateTime;
        this.attendanceStatus = attendanceStatus;
        this.coverWorkId = coverWorkId;
    }

    public static CoverAttendance newCoverAttendance(CoverWork coverWork, LocalDate now) {
        return new CoverAttendance(
                new StoreInfo(coverWork.getCoverManager().getStoreInfo()),
                new WorkerId(coverWork.getCoverManager().getAcceptedStaff().getId(), coverWork.getCoverManager().getAcceptedStaff().getName()),
                new WorkDateTime(coverWork.getCoverDateTime().getStartTime(), coverWork.getCoverDateTime().getEndTime(), now),
                coverWork.getCoverWorkId(),
                AttendanceStatus.EXPECT);
    }

    private void isNotWorkStartTime(LocalTime now) {
        if (now.isBefore(workDateTime.getStartTime().minusMinutes(10))) {
            throw new IllegalStateException("아직 출근 시간이 아닙니다.");
        }
    }

    public void isNotWorkOffTime(LocalTime now, Long allowedLeaveTime) {
        if (now.isBefore(workDateTime.getEndTime().minusMinutes(allowedLeaveTime))) {
            throw new IllegalStateException("아칙 퇴근 시간이 아닙니다.");
        }
    }

    public void checkInCoverWork(LocalDateTime now, Long allowedLateTime) {
        LocalTime nowTime = now.toLocalTime();
        isNotWorkStartTime(now.toLocalTime());
        if (nowTime.isAfter(this.workDateTime.getStartTime().minusMinutes(10))
                && nowTime.isBefore(this.workDateTime.getEndTime().plusMinutes(10))) {
            this.checkInTime = now;
            this.attendanceStatus = AttendanceStatus.CHECK_IN;
            //Events.raise(정상 출근 알림);
        }

        if (nowTime.isAfter(this.workDateTime.getStartTime().plusMinutes(10))
                && nowTime.isBefore(this.workDateTime.getStartTime().plusMinutes(allowedLateTime))) {
            this.checkInTime = now;
            this.attendanceStatus = AttendanceStatus.LATE;
            //Events.raise(지각 알림);
        }

        if (nowTime.isAfter(this.workDateTime.getStartTime().plusMinutes(allowedLateTime))) {
            this.checkInTime = now;
            this.attendanceStatus = AttendanceStatus.ABSENCE;
            //Events.raise(결근 알림);
        }
    }

    public void checkOutCoverWork(LocalDateTime now, Long allowedLeaveTime){
        isNotWorkOffTime(now.toLocalTime(), allowedLeaveTime);
        this.checkOutTime = now;
        this.attendanceStatus = AttendanceStatus.CHECK_OUT;
    }
}
