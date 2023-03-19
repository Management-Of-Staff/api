package com.example.sidepot.work.app;

import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.member.domain.*;
import com.example.sidepot.work.dao.StaffWork;
import com.example.sidepot.work.domain.*;
import com.example.sidepot.work.dto.EmploymentReadDto.*;
import com.example.sidepot.work.dto.EmploymentUpdateDto.*;
import com.example.sidepot.store.domain.Store;
import com.example.sidepot.store.domain.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmploymentService {
    private final StaffRepository staffRepository;
    private final StoreRepository storeRepository;
    private final EmploymentRepository employmentRepository;

    @Transactional(readOnly = true)
    public ReadOneEmploymentResponse readEmploymentDetail(LoginMember member, Long employmentId){
        Employment employment = employmentRepository.findById(employmentId)
                .orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND_STAFF_IN_STORE));
        return ReadOneEmploymentResponse.of(employment);
    }

    @Transactional
    public void withdrawEmployment(LoginMember member, Long employmentId){
        Employment employment = employmentRepository.findById(employmentId)
                .orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND_EMPLOYMENT));
        employment.withdrawEmployment();
        employmentRepository.save(employment);
    }

    @Transactional
    public void updateEmploymentRankAndWage(LoginMember member, Long employmentId,
                                            UpdateRankAndWageRequest updateRankAndWageRequest) {
        Employment employment = employmentRepository.findById(employmentId)
                .orElseThrow(() -> new Exception(ErrorCode.NOT_FOUND_EMPLOYMENT));
        employment.updateRankAndWage(updateRankAndWageRequest);
        employmentRepository.save(employment);
    }

    @Transactional
    public void createEmployment(Long storeId, Long staffId){
        Store store = storeRepository.findById(storeId)
                .orElseThrow(()-> new Exception(ErrorCode.NOT_FOUND_YOUR_STORE));
        Staff staff = staffRepository.findById(staffId)
                .orElseThrow(()-> new Exception(ErrorCode.MEMBER_NOT_FOUND));
        if(employmentRepository.existsByStaff_MemberIdAndStore_StoreId(storeId, staffId)){
            throw new Exception(ErrorCode.ALREADY_STAFF_REGISTRATION);
        }
        employmentRepository.save(Employment.createEmployment(store, staff));
    }
}
