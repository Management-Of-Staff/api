package com.example.sidepot.global.dummy;

import com.example.sidepot.command.attendance.domain.WorkerId;
import com.example.sidepot.command.employment.domain.Employment;
import com.example.sidepot.command.member.domain.Owner;
import com.example.sidepot.command.member.domain.Staff;
import com.example.sidepot.command.store.domain.Store;
import com.example.sidepot.command.work.domain.*;
import com.example.sidepot.command.work.dto.CoverWorkRequestDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class AppDummyObject {

    protected Owner newOwner(String name, String phoneNum) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("1234");
        return Owner.registerOwner(name, encPassword, phoneNum, LocalDateTime.now());
    }

    protected Staff newStaff(String name, String phoneNum) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("1234");
        return Staff.registerStaff(name, encPassword, phoneNum, LocalDateTime.now());
    }

    protected Store newStore(String storeName, Owner owner) {
        return Store.builder().owner(owner).branchName(storeName).build();
    }

    protected Employment newEmployment(Store store, Staff staff) {
        return Employment.createEmployment(store, staff);
    }

    protected WorkTime newWorkTime(Store store, Staff staff,
                                   LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek) {
        return WorkTime.builder()
                .store(store).staff(staff)
                .startTime(startTime).endTime(endTime)
                .dayOfWeek(dayOfWeek)
                .isDeleted(false)
                .build();
    }

    protected WorkManager newWorkManager(Store storePs, Staff staffPs, List<WorkTime> workTimeList) {
        return new WorkManager(
                new StoreInfo(storePs.getStoreId(), storePs.getBranchName(), storePs.getStoreName()),
                new WorkerId(staffPs.getMemberId(), staffPs.getMemberName()),
                workTimeList);
    }

    protected CoverWork newCoverWork(Staff rStaff, Staff aStaff, LocalDate localDate, WorkTime workTime) {
        return new CoverWork(
                new RequestedStaff(rStaff.getMemberId(), rStaff.getMemberName()),
                new AcceptedStaff(aStaff.getMemberId(), aStaff.getMemberName()),
                new CoverDateTime(localDate, workTime.getStartTime(), workTime.getEndTime()),
                new WorkTimeId(workTime.getWorkTimeId()));
    }

    protected  CoverManager newCoverManager(Store store, Staff rStaff, Staff aStaff, List<CoverWork> coverWorkList){
        return new CoverManager(
                new StoreInfo(store.getStoreId(), store.getBranchName(), store.getStoreName()),
                new RequestedStaff(rStaff.getMemberId(), rStaff.getMemberName()),
                new AcceptedStaff(aStaff.getMemberId(), aStaff.getMemberName()),
                coverWorkList
        );
    }
}
