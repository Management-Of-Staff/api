package com.example.sidepot.work.domain.refactor.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Access(AccessType.FIELD)
@Table(name = "new_employment")
@Entity
public class NewEmployment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "new_employment_id")
    private Long newEmploymentId;

    @Embedded
    private StoreId storeId;

    @Embedded
    private StaffId staffId;

    @Embedded
    private WorkContract workContract;

    @Embedded
    private WorkingPosition workingPosition;

    @ElementCollection
    @CollectionTable(name = "day_of_week",
            joinColumns = @JoinColumn(name = "new_employment"))
    @Column(name = "working_days")
    private Set<DayOfWeek> days = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "daily_work",
            joinColumns = @JoinColumn(name = "weekly_work_id"))
    @OrderColumn(name = "daily_work_idx")
    private List<DailyWork> dailyWorks = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Attendance currentWorkStatus;

    public NewEmployment(StoreId storeId, StaffId staffId,
                         Set<DayOfWeek> days, WorkContract workContract) {
        setStoreId(storeId);
        setStaffId(staffId);
        setWorkContract(workContract);
        setDays(days);
        this.currentWorkStatus = Attendance.ABSENT;
    }

    public static NewEmployment initialEmployment(StoreId storeId, StaffId staffId){
        NewEmployment newEmployment = new NewEmployment();
        newEmployment.setStoreId(storeId);
        newEmployment.setStaffId(staffId);
        return newEmployment;
    }

    private void setStoreId(StoreId storeId){
        if(storeId == null) throw new IllegalArgumentException("매장 정보가 없습니다.");
        this.storeId = storeId;
    }

    private void setStaffId(StaffId staffId){
        if(staffId == null) throw new IllegalArgumentException("직원 정보가 없습니다.");
        this.staffId = staffId;
    }

    private void setWorkContract(WorkContract workContract){
        if(workContract == null) throw new IllegalArgumentException("근무 정보가 없습니다.");
        this.workContract = workContract;
    }

    private void setDays(Set<DayOfWeek> days) {
        if(days == null) throw new IllegalArgumentException("요일 정보가 없습니다.");
        this.days = days;
    }

    private void addDailyWork(DailyWork newDailyWork) {
        this.dailyWorks.add(newDailyWork);
        setCurrentWorkingStatus(newDailyWork);
    }

    //기획 없음
    private void updateDailyWork(DailyWork newDailyWork){};

    private void setCurrentWorkingStatus(DailyWork newDailyWork) {
        LocalTime now = LocalTime.now();
        LocalTime attendanceTime = LocalTime.from(newDailyWork.getRealWorkTime().getRealStartTime());
        availableCheck(now, attendanceTime);
        if(now.isBefore(attendanceTime.plusMinutes(10))) this.currentWorkStatus = Attendance.NORMAL;
        else if(now.isAfter(attendanceTime.plusMinutes(10))) this.currentWorkStatus = Attendance.TARDY;
    }

    private void availableCheck(LocalTime now, LocalTime attendanceTime) {
        if(now.isBefore(attendanceTime)) throw new IllegalStateException("출석 가능한 시간이 아닙니다.");
    }
}
