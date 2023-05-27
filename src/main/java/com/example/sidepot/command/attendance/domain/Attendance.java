package com.example.sidepot.command.attendance.domain;

import com.example.sidepot.command.work.domain.StoreInfo;
import com.example.sidepot.command.work.domain.WorkTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private StoreInfo storeInfo;

    @Embedded
    private WorkerId workerId;

    @Embedded
    private WorkDateTime workDateTime;

    @Column(name = "work_time_id")
    private Long workTimeId;

    @Column(name = "check_in_time")
    private LocalDateTime checkInTime;

    @Column(name = "check_out_time")
    private LocalDateTime checkOutTime;

    @Column(name = "attendance_status")
    @Enumerated(EnumType.STRING)
    private AttendanceStatus attendanceStatus;

    @Column(name = "real_work_time")
    private LocalTime real_work_time;

    public Attendance(StoreInfo storeInfo, WorkerId workerId, WorkDateTime workDateTime,
                      Long workTimeId, AttendanceStatus attendanceStatus){
        this.storeInfo = storeInfo;
        this.workTimeId = workTimeId;
        this.workDateTime = workDateTime;
        this.attendanceStatus = attendanceStatus;
        this.workerId = workerId;
    }

    public static Attendance newAttendance(WorkTime workTime, LocalDate now) {
        return new Attendance(
                new StoreInfo(workTime.getStore().getStoreId(), workTime.getStore().getBranchName(), workTime.getStore().getStoreName()),
                new WorkerId(workTime.getStaff().getMemberId(), workTime.getStaff().getMemberName()),
                new WorkDateTime(workTime.getStartTime(), workTime.getEndTime(), now),
                workTime.getWorkTimeId(),
                AttendanceStatus.EXPECT);
    }

    private void isNotWorkStartTime(LocalDateTime now) {
        if (checkInTime.isBefore(now.minusMinutes(10))) {
            throw new IllegalStateException("아직 출근 시간이 아닙니다.");
        }
    }

    private void isNotWorkOffTime(LocalDateTime now, Long allowedLeaveTime) {
        if (checkOutTime.isBefore(now.minusMinutes(allowedLeaveTime))) {
            throw new IllegalStateException("아칙 퇴근 시간이 아닙니다.");
        }
    }

    public void checkInCWork(LocalDateTime now, Long allowedLateTime) {
        isNotWorkStartTime(now);
        if (now.isAfter(this.checkInTime.minusMinutes(10)) && now.isBefore(this.checkInTime.plusMinutes(10))) {
            this.checkInTime = now;
            this.attendanceStatus = AttendanceStatus.CHECK_IN;
            //Events.raise(정상 출근 알림);
        }

        if (now.isAfter(this.checkInTime) && now.isBefore(this.checkInTime.plusMinutes(allowedLateTime))) {
            this.checkInTime = now;
            this.attendanceStatus = AttendanceStatus.LATE;
            //Events.raise(지각 알림);
        }

        if (now.isAfter(this.checkInTime.plusMinutes(allowedLateTime))) {
            this.checkInTime = now;
            this.attendanceStatus = AttendanceStatus.ABSENCE;
            //Events.raise(결근 알림);
        }
    }

    public void checkOutWork(LocalDateTime now, Long allowedLeaveTime){
        isNotWorkOffTime(now, allowedLeaveTime);
        this.checkOutTime = now;
        this.attendanceStatus = AttendanceStatus.CHECK_OUT;
    }
}
