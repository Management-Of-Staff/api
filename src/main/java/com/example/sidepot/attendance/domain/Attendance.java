package com.example.sidepot.attendance.domain;


import com.example.sidepot.employment.domain.Employment;
import com.example.sidepot.global.domain.BaseEntity;
import com.example.sidepot.store.domain.Store;
import com.example.sidepot.store.dto.WorkTimeDto;
import io.jsonwebtoken.lang.Assert;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity @Getter
@Table(name = "attendance")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attendance extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employment_id")
    private Employment employment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "check_in_time", nullable = false)
    private LocalDateTime checkInTime;

    @Column(name = "check_out_time")
    private LocalDateTime checkOutTime;

    @Column(name = "attendance_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AttendanceStatus attendanceStatus;

    public String getStaffName() {
        Assert.notNull(employment, "Employment must not be null");
        return employment.getStaff().getMemberName();
    }

    public String getPhoneNumber() {
        Assert.notNull(employment, "Employment must not be null");
        return employment.getPhoneNumber();
    }

    @Builder
    private Attendance(Employment employment, Store store, LocalDateTime checkInTime, LocalDateTime checkOutTime,
                       AttendanceStatus attendanceStatus) {
        this.employment = employment;
        this.store = store;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.attendanceStatus = attendanceStatus;
    }

    /**
     * 등록된 근무 시간 조회
     */
    public List<WorkTimeDto> getRegisteredWorkingTime() {
        return WorkTimeDto.fromList(employment.getWorkTimeList());
    }

    public static Attendance ofCheckIn(Store store, Employment employment) {
        Assert.notNull(store, "Store must not be null");
        Assert.notNull(employment, "Employment must not be null");
        return Attendance.builder()
            .store(store)
            .employment(employment)
            .attendanceStatus(AttendanceStatus.CHECK_IN)
            .checkInTime(LocalDateTime.now())
            .build();
    }

    public void setCheckOutTime() {
        this.attendanceStatus = AttendanceStatus.CHECK_OUT;
        this.checkOutTime = LocalDateTime.now();
    }

    public boolean isCheckOut() {
        return this.attendanceStatus.isCheckOut();
    }
}
