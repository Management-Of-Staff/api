package com.example.sidepot.command.employment.app;

import com.example.sidepot.command.employment.domain.Employment;
import com.example.sidepot.command.employment.dto.EmploymentUpdateDto.*;
import com.example.sidepot.command.employment.repository.EmploymentDaoRepository;
import com.example.sidepot.command.employment.repository.EmploymentRepository;
import com.example.sidepot.command.member.domain.Staff;
import com.example.sidepot.command.member.domain.StaffRepository;
import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.command.store.domain.Store;
import com.example.sidepot.command.store.domain.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EmploymentCommandService {
    private final StaffRepository staffRepository;
    private final StoreRepository storeRepository;
    private final EmploymentRepository employmentRepository;
    private final EmploymentDaoRepository employmentDaoRepository;

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
        // #DAO
        if(employmentDaoRepository.existsByStaffAndStore(staffId, storeId)){
            throw new Exception(ErrorCode.ALREADY_STAFF_REGISTRATION);
            //직원을 삭제했으면? 등록되어있다고 알려주고 삭제된 직원 목록?
        }
        employmentRepository.save(Employment.createEmployment(store, staff));
    }
}