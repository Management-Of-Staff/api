package com.example.sidepot.work.domain;

import com.example.sidepot.global.domain.BaseEntity;
import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.member.domain.Rank;
import com.example.sidepot.member.domain.Staff;
import com.example.sidepot.store.domain.AttendanceStatus;
import com.example.sidepot.work.dto.EmploymentUpdateDto.*;
import com.example.sidepot.store.domain.Store;
import com.example.sidepot.work.dto.WorkTimeRequest;
import com.example.sidepot.work.dto.WorkTimeRequest.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.jdbc.Work;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public void createWorkSchedule(WorkAddRequest workAddRequest) {
        workAddRequest.getDayOfWeekList().stream()
                .forEach(day -> this.addWorkTime(WorkTime.createWorkTime(day,
                        workAddRequest.getStartTime(),
                        workAddRequest.getEndTime())));
    }

    public void deleteWorkSchedule(Set<Long> workTimeIds){
        Set<WorkTime> s = this.workTimeList.stream()
                .filter(workTime -> workTimeIds.contains(workTime.getWorkTimeId()))
                .collect(Collectors.toSet());
        s.forEach(this::removeWorkTime);
    }

    public void checkOverlappingSchedule(Set<DayOfWeek> days, LocalTime startTime, LocalTime endTime){
        LocalTime checkStartTime = startTime.plusSeconds(1);
        LocalTime checkEndTime = endTime.plusSeconds(1);
        if (workTimeList.stream()
                .anyMatch(workTime ->
                        (checkStartTime.isAfter(workTime.getStartTime()) && checkStartTime.isBefore(workTime.getEndTime()) && days.contains(workTime.getDay()))
                                || (checkEndTime.isAfter(workTime.getStartTime()) && checkEndTime.isBefore(workTime.getEndTime()) && days.contains(workTime.getDay())))) {
            throw new IllegalArgumentException("등록하고자 하는 시간에 근무 시간이 있습니다.");
        }
    }

    private void addWorkTime(WorkTime workTime){
        this.workTimeList.add(workTime);
        workTime.setEmployment(this);
    }

    private void removeWorkTime(WorkTime workTime) {
        this.workTimeList.remove(workTime);
        workTime.remove();
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
