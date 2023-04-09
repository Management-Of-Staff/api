package com.example.sidepot.work.domain;

import com.example.sidepot.global.domain.BaseEntity;
import com.example.sidepot.member.domain.Rank;
import com.example.sidepot.member.domain.Staff;
import com.example.sidepot.attendance.domain.AttendanceStatus;
import com.example.sidepot.work.dto.EmploymentUpdateDto.*;
import com.example.sidepot.store.domain.Store;
import com.example.sidepot.work.dto.WorkTimeRequest.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "employment",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    private List<CoverWorkTime> coverWorkTimeList = new ArrayList<>();

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
    /**
     * 대타 필요한 직원이 매장에 대타 구함 요청을 만들 때, 요청한 직원의 대타근무를 생성 할 필요가 있는가? 수락할 때 만들면 되지 않나,,
     * 1. 임시로 요청에 대한 대타 근무를 생성하고 일정 기간 대체할 근무자가 없으면 삭제 (create_update) -> 어짜피 얘도 매장으로 알림 보내야함
     * 2. 요청만 생성해서 매장 알림으로 보내고, 수락되면 생성(create)
     */
    public void addRequestedCoverWork(addPinchTimeReqDto addPinchTimeReqDto){
       /*
        예외처리 추가, 생성할 대타(같은 날짜)가 이미 있으면, 또는 생성된 대타의 정보(같은 날짜, 수락직원)이 있으면 예외
        */
        this.addCoverWorkTime(CoverWorkTime.createCoverWorkRequest(
                addPinchTimeReqDto.workTimeId,
                addPinchTimeReqDto.requestStaffId,
                addPinchTimeReqDto.pinchDate,
                addPinchTimeReqDto.startTime,
                addPinchTimeReqDto.endTime));
    }
    /**
     * 대타 구함 요청을 어느 직원이 수락했을 때, 요청한 직원의 대타를 업데이트
     */
    public void requestedCoverWorkOk(AcceptPinchWorkReqDto acceptPinchWorkReqDto){
        CoverWorkTime coverWorkTime = this.coverWorkTimeList.stream()
                .filter(p -> p.getWorkTimeId().equals(acceptPinchWorkReqDto)).findFirst().get();// 예외처리를 통해 반드시 하나의 pinchTime 이 로딩 되어야함
        coverWorkTime.accomplishCoverWork(acceptPinchWorkReqDto.acceptedStaffId);
    }
    /**
     * 수락한 직원의 대타 근무를 생성
     */
    public void addAcceptPinchWork(AddAcceptPinchWorkTimeDto addAcceptPinchWorkTimeDto){
        this.addCoverWorkTime(CoverWorkTime.createAcceptedCoverWork(
                addAcceptPinchWorkTimeDto.workTimeId,
                addAcceptPinchWorkTimeDto.requestStaffId,
                addAcceptPinchWorkTimeDto.acceptedStaffId,
                addAcceptPinchWorkTimeDto.pinchDate,
                addAcceptPinchWorkTimeDto.startTime,
                addAcceptPinchWorkTimeDto.endTime
        ));
    }

    private void addCoverWorkTime(CoverWorkTime coverWorkTime){
        this.coverWorkTimeList.add(coverWorkTime);
        coverWorkTime.setEmployment(this);
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
