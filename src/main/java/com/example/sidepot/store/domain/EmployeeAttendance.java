package com.example.sidepot.store.domain;

import com.example.sidepot.global.domain.BaseEntity;
import com.example.sidepot.member.domain.Employment;
import com.example.sidepot.store.dto.WorkTimeDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity @Getter
@Table(name = "employee_attendance")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmployeeAttendance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
        // 에러 반환 예정
        if(employment == null) {
            return "";
        }
        return employment.getStaffName();
    }

    public String getPhoneNumber() {
        // 에러 반환 예정
        if(employment == null) {
            return "";
        }
        return employment.getPhoneNumber();
    }

    /**
     * 등록된 근무 시간 조회
     */
    public List<WorkTimeDto> getRegisteredWorkingTime() {
        return WorkTimeDto.fromList(employment.getWeekWorkTimeList());
    }
}
