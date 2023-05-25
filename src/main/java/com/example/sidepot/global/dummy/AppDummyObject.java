package com.example.sidepot.global.dummy;

import com.example.sidepot.command.employment.domain.Employment;
import com.example.sidepot.command.member.domain.Owner;
import com.example.sidepot.command.member.domain.Staff;
import com.example.sidepot.command.store.domain.Store;
import com.example.sidepot.command.work.domain.WorkTime;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AppDummyObject {

    protected Owner newOwner(String name, String phoneNum){
        BCryptPasswordEncoder passwordEncoder  = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("1234");
        return Owner.registerOwner(name, encPassword, phoneNum, LocalDateTime.now());
    }

    protected Staff newStaff(String name, String phoneNum){
        BCryptPasswordEncoder passwordEncoder  = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("1234");
        return Staff.registerStaff(name, encPassword, phoneNum, LocalDateTime.now());
    }

    protected Store newStore(String storeName, Owner owner){
        return Store.builder().owner(owner).branchName(storeName).build();
    }

    protected Employment newEmployment(Store store, Staff staff){
        return Employment.createEmployment(store, staff);
    }

    protected WorkTime newWorkTime(Store store, Staff staff,
                                   LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek){
        return WorkTime.builder()
                .store(store).staff(staff)
                .startTime(startTime).endTime(endTime)
                .dayOfWeek(dayOfWeek)
                .isDeleted(false)
                .build();
    }
//
//    protected CoverWork newCoverWork(Long storeId, String storeName, Long acceptedStaffId, Long requestedStaffId,
//                                     CoverWorkNotice coverWorkNotice, LocalDate coverDate,
//                                     LocalTime startTime, LocalTime endTime, DayOfWeek dayOfWeek, boolean isAccepted){
//        return CoverWork.builder()
//                .workingStoreId(storeId)
//                .workingStoreName(storeName)
//                .acceptedStaffId(acceptedStaffId)
//                .requestedStaffId(requestedStaffId)
//                .coverWorkNotice(coverWorkNotice)
//                .coverDate(coverDate)
//                .startTime(startTime)
//                .endTime(endTime)
//                .dayOfWeek(dayOfWeek)
//                .isAccepted(isAccepted)
//                .successCheck(false)
//                .build();
//    }
//
//    protected CoverWorkNotice newCoverWorkNotice(Long storeId, Long staffId){
//        return CoverWorkNotice.builder()
//                .storeId(storeId)
//                .staffId(staffId)
//                .coverNoticeType(CoverNoticeType.REQUESTED)
//                .readStatus(false)
//                .isAccepted(false)
//                .build();
//    }
}
