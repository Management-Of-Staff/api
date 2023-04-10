package com.example.sidepot.work.app;

import com.example.sidepot.global.error.ErrorCode;
import com.example.sidepot.global.error.Exception;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.work.domain.*;
import com.example.sidepot.work.dto.WorkTimeRequest.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class WorkTimeCommandService {

    private final EmploymentRepository employmentRepository;

    @Transactional
    public void createEmploymentWorkSchedule(LoginMember member, Long employmentId,
                                             WorkAddRequest workAddRequest){
        List<Employment> employmentList
                = employmentRepository.findAllByStaff_MemberId(workAddRequest.getStaffId());

        isPossibleWork(workAddRequest, employmentList);
        Employment employment = employmentList.stream()
                .filter(e -> e.getEmploymentId().equals(employmentId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("일치하는 근무가 없습니다."));
        employment.createWorkSchedule(workAddRequest);
        employmentRepository.save(employment);
    }

    @Transactional
    public void updateEmploymentWorkSchedule(LoginMember member, Long employmentId,
                                             WorkUpdateRequest workUpdateRequest){

        List<Employment> employmentList
                = employmentRepository.findAllByStaff_MemberId(workUpdateRequest.getWorkAddRequest().getStaffId());

        isPossibleWork(workUpdateRequest.getWorkAddRequest(), employmentList);
        Employment employment = employmentList.stream()
                .filter(e -> e.getEmploymentId().equals(employmentId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("일치하는 근무가 없습니다."));
        employment.deleteWorkSchedule(workUpdateRequest.getWorkTimeIds());
        employment.createWorkSchedule(workUpdateRequest.getWorkAddRequest());
        employmentRepository.save(employment);
    }

    @Transactional
    public void deleteEmploymentWorkSchedule(LoginMember member, Long employmentId,
                                             WorkDeleteRequest workDeleteRequest) {
        employmentRepository.findById(employmentId)
                .ifPresentOrElse(
                        employment -> employment.deleteWorkSchedule(workDeleteRequest.getWorkTimeIds()),
                        () -> {throw new IllegalArgumentException("일치하는 근무가 없습니다.");}
                );
    }

    private void isPossibleWork(WorkAddRequest workAddRequest, List<Employment> employmentList) {
        employmentList.forEach(
                e -> e.checkOverlappingSchedule(
                        workAddRequest.getDayOfWeekList(),
                        workAddRequest.getStartTime(),
                        workAddRequest.getEndTime()));
    }
}
