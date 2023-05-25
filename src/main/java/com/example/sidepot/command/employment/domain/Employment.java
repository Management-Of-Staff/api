package com.example.sidepot.command.employment.domain;

import com.example.sidepot.command.attendance.domain.AttendanceStatus;
import com.example.sidepot.global.domain.BaseEntity;
import com.example.sidepot.command.member.domain.Rank;
import com.example.sidepot.command.member.domain.Staff;
import com.example.sidepot.command.work.domain.WorkTime;
import com.example.sidepot.command.employment.dto.EmploymentUpdateDto.*;
import com.example.sidepot.command.store.domain.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Table(name = "employment")
@Entity
public class Employment extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employment_id")
    private Long employmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @OneToMany(mappedBy = "employment",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    private List<WorkTime> workTimeList = new ArrayList<>();

    @Column(name = "hourly_wage")
    private Long hourlyWage;

    @Column(name = "rank")
    private Rank rank;

    @Column(name ="health_certificate")
    private boolean healthCertificate;

    @Column(name = "attendance_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AttendanceStatus attendanceStatus;

    @Column(name = "withdrawal_status", nullable = false)
    private Boolean withdrawal_status;

    private Employment(Store store, Staff staff) {
        this.store = store;
        this.staff = staff;
        this.healthCertificate = false;
        this.attendanceStatus = AttendanceStatus.INITIAL;
        this.withdrawal_status = false;
    }

    public static Employment createEmployment(Store store, Staff staff){
       return new Employment(store, staff);
    }

    public void updateRankAndWage(UpdateRankAndWageRequest updateRankAndWageRequest) {
        this.rank = updateRankAndWageRequest.getRank();
        this.hourlyWage = updateRankAndWageRequest.getHourlyWage();
    }
    public void withdrawEmployment(){
        this.withdrawal_status = true;
    }


    /**
     * 폰 번호 조회
     */
    public String getPhoneNumber() {
        if (staff == null) {
            return "";
        }
        String phoneNumber = staff.getMemberPhoneNum();
        if (!StringUtils.hasText(phoneNumber)) {
            return "전화번호가 등록되어 있지 않습니다.";
        }
        return phoneNumber;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    private static class AddAcceptPinchWorkTimeDto {
        private Long workTimeId;
        private Long requestStaffId;
        private Long acceptedStaffId;
        private Long acceptedTime;
        private LocalDate pinchDate;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class addPinchTimeReqDto{
        private Long workTimeId;
        private Long requestStaffId;
        private LocalDate pinchDate;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
    }
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class AcceptPinchWorkReqDto{
        private Long workTimeId;
        private Long requestStaffId;
        private Long acceptedStaffId;
        private Long acceptedTime;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
    }
}
