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

    @Embedded

    @Column(name = "check_in_time", nullable = false)
    private LocalDateTime checkInTime;

    @Column(name = "check_out_time")
    private LocalDateTime checkOutTime;

    @Column(name = "attendance_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AttendanceStatus attendanceStatus;

    @Column(name = "real_work_time")
    private LocalTime real_work_time;

    public Attendance(StoreInfo storeInfo, WorkerId workerId,
                      WorkDateTime workDateTime, AttendanceStatus attendanceStatus){
        this.storeInfo = storeInfo;
        this.workerId = workerId;
        this.workDateTime = workDateTime;
        this.attendanceStatus = attendanceStatus;
    }

    public static Attendance newAttendance(WorkTime workTime, LocalDate now) {
        return new Attendance(
                new StoreInfo(workTime.getStore().getStoreId(), workTime.getStore().getBranchName(), workTime.getStore().getStoreName()),
                new WorkerId(workTime.getStaff().getMemberId(), workTime.getStaff().getMemberName()),
                new WorkDateTime(workTime.getStartTime(), workTime.getEndTime(), workTime.getDayOfWeek(), now),
                AttendanceStatus.EXPECT);
    }

}
