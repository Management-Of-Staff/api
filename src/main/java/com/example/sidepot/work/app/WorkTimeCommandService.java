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

        //개념적으로 도메인 모델에서, 직원 도메인과 근무 도메인이 모두 사용될 때 이벤트 및 도메인 서비스를 만들지만,
        //모놀리틱 서버이며, 모든 도메인이 JPA 기술로 직접 연관되어 있기 때문에 도메인 서비스를 생략하고
        //JPA 기술 "외래키_필드명" 도메인 직접 참조를 채택
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
