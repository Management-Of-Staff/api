package com.example.sidepot.work.domain;

import com.example.sidepot.member.domain.Rank;
import com.example.sidepot.member.domain.Staff;
import com.example.sidepot.store.domain.AttendanceStatus;
import com.example.sidepot.work.dto.EmploymentUpdateDto.*;
import com.example.sidepot.store.domain.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Table(name = "employment")
@Entity
public class Employment {

    @Id @GeneratedValue
    @Column(name = "employment_id")
    private Long employmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @OneToMany(mappedBy = "employment")
    private List<WeekWorkTime> weekWorkTimeList = new ArrayList<>();

    @Column(name = "hourly_wage")
    private Long hourlyWage;

    @Column(name = "rank")
    private Rank rank;

    @Column(name ="health_certificate")
    private boolean healthCertificate;

    @Column(name = "attendance_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AttendanceStatus attendanceStatus;


    private Employment(Store store, Staff staff) {
        this.store = store;
        this.staff = staff;
        this.healthCertificate = false;
        this.attendanceStatus = AttendanceStatus.INITIAL;
    }

    public static Employment createEmployment(Store store, Staff staff){
       return new Employment(store, staff);
    }

    public void updateRankAndWage(UpdateRankAndWageRequest updateRankAndWageRequest) {
        this.rank = updateRankAndWageRequest.getRank();
        this.hourlyWage = updateRankAndWageRequest.getHourlyWage();
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
}
