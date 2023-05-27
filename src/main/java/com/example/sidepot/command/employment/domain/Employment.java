package com.example.sidepot.command.employment.domain;

import com.example.sidepot.command.attendance.domain.AttendanceStatus;
import com.example.sidepot.global.domain.BaseEntity;
import com.example.sidepot.command.member.domain.Rank;
import com.example.sidepot.command.member.domain.Staff;
import com.example.sidepot.command.employment.dto.EmploymentUpdateDto.*;
import com.example.sidepot.command.store.domain.Store;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @Column(name = "hourly_wage")
    private Long hourlyWage;

    @Column(name = "rank")
    private Rank rank;

    @Column(name ="health_certificate")
    private boolean healthCertificate;

    @Column(name = "attendance_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AttendanceStatus attendanceStatus;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted;

    private Employment(Store store, Staff staff) {
        this.store = store;
        this.staff = staff;
        this.healthCertificate = false;
        this.attendanceStatus = AttendanceStatus.INITIAL;
        this.isDeleted = false;
    }

    public static Employment createEmployment(Store store, Staff staff){
       return new Employment(store, staff);
    }

    public void updateRankAndWage(UpdateRankAndWageRequest updateRankAndWageRequest) {
        this.rank = updateRankAndWageRequest.getRank();
        this.hourlyWage = updateRankAndWageRequest.getHourlyWage();
    }
    public void withdrawEmployment(){
        this.isDeleted = true;
    }
}
