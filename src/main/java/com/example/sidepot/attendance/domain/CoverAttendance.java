package com.example.sidepot.attendance.domain;

import com.example.sidepot.work.domain.StoreInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cover_attendance")
@Entity
public class CoverAttendance {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private StoreInfo storeInfo;

    @Embedded
    private WorkerId workerId;

    @Column(name = "check_in_time", nullable = false)
    private LocalDateTime checkInTime;

    @Column(name = "check_out_time")
    private LocalDateTime checkOutTime;

    @Column(name = "attendance_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AttendanceStatus attendanceStatus;

    @Column(name = "realWorkTime")
    private LocalTime realWorkTime;

    public CoverAttendance(StoreInfo storeInfo, WorkerId workerId, LocalDateTime checkInTime, AttendanceStatus attendanceStatus) {
        this.storeInfo = storeInfo;
        this.workerId = workerId;
        this.checkInTime = checkInTime;
        this.attendanceStatus = attendanceStatus;
        //Events.raise(출근 알림);
        //Events.raise(고용 근무 상태 변경)
    }

    public void isNotWorkStartTime(LocalDateTime now){
        if(checkInTime.isBefore(now.minusMinutes(10))){
            throw new IllegalStateException("아직 출근 시간이 아닙니다.");
        }
    }

    public void isNotWorkOffTime(LocalDateTime now, Long allowedLeaveTime){
        if (checkOutTime.isBefore(now.minusMinutes(allowedLeaveTime))){
            throw new IllegalStateException("아칙 퇴근 시간이 아닙니다.");
        }
    }

    private void checkIn(LocalDateTime now, Long allowedLateTime){
        if(now.isAfter(this.checkInTime.minusMinutes(10)) && now.isBefore(this.checkInTime.plusMinutes(10))){
            this.checkInTime = now;
            this.attendanceStatus = AttendanceStatus.CHECK_IN;
            //Events.raise(정상 출근 알림);
            //Events.raise(근무 상태 변경);
        }

        if(now.isAfter(this.checkInTime) && now.isBefore(this.checkInTime.plusMinutes(allowedLateTime))){
            this.checkInTime = now;
            this.attendanceStatus = AttendanceStatus.LATE;
            //Events.raise(지각 알림);
            //Events.raise(근무 상태 변경);
        }

        if(now.isAfter(this.checkInTime.plusMinutes(allowedLateTime))){
            this.checkInTime = now;
            this.attendanceStatus = AttendanceStatus.ABSENCE;
            //Events.raise(결근 알림);
            //Events.raise(근무 상태 변경);
        }
    }
}
