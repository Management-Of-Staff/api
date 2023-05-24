package com.example.sidepot.attendance.domain;


import com.example.sidepot.global.domain.BaseEntity;
import com.example.sidepot.work.domain.StoreInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity @Getter
@Table(name = "attendance")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attendance extends BaseEntity {
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

    @Column(name = "real_work_time")
    private LocalTime real_work_time;


//    public static Attendance ofCheckIn(Store store, Employment employment) {
//        Assert.notNull(store, "Store must not be null");
//        Assert.notNull(employment, "Employment must not be null");
//        return Attendance.builder()
//            .store(store)
//            .employment(employment)
//            .attendanceStatus(AttendanceStatus.CHECK_IN)
//            .checkInTime(LocalDateTime.now())
//            .build();
//    }

    public void setCheckOutTime() {
        this.attendanceStatus = AttendanceStatus.CHECK_OUT;
        this.checkOutTime = LocalDateTime.now();
    }

    public boolean isCheckOut() {
        return this.attendanceStatus.isCheckOut();
    }
}
