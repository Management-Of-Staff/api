package com.example.sidepot.command.work.app;


import com.example.sidepot.command.work.domain.CoverWork;
import com.example.sidepot.command.work.domain.WorkTime;
import com.example.sidepot.command.work.dto.AttendanceTodayResDto;
import com.example.sidepot.global.DomainService;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.command.work.repository.query.CoverWorkDaoRepository;
import com.example.sidepot.command.work.repository.query.WorkTimeDaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@RequiredArgsConstructor
@DomainService
public class AttendanceExtractService {

    private final WorkTimeDaoRepository workTimeDaoRepository;
    private final CoverWorkDaoRepository coverWorkDaoRepository;
    /**
     * 로그인 한 직원과 오늘 날짜로 출/퇴근 정보를 계산하는 서비스
     */
    @Transactional(readOnly = true)
    public AttendanceTodayResDto extractWorkScheduleOnDay(LoginMember member, LocalDate onDay){
        //대타가 없는 고정근무를 조회힌다.
        List<WorkTime> staffWorksByOnDay = getStaffWorksByOnDay(member, onDay);
        //수락한 대타를 조회한다.
        List<CoverWork> staffAcceptedCoversByOnDay = getStaffAcceptedCoversByOnDay(member, onDay);

        //출근 정보 유무
        isEmptyWork(staffWorksByOnDay, staffAcceptedCoversByOnDay);

        return new AttendanceTodayResDto(staffWorksByOnDay, staffAcceptedCoversByOnDay);
    }

    private void isEmptyWork(List<WorkTime> staffWorksByOnDay, List<CoverWork>  staffAcceptedCoversByOnDay){
        if ((staffWorksByOnDay == null || staffWorksByOnDay.isEmpty())
                && (staffAcceptedCoversByOnDay == null || staffAcceptedCoversByOnDay.isEmpty())){
                throw new IllegalStateException("오늘은 출근 정보가 없네요");
        }
    }

    private List<CoverWork> getStaffAcceptedCoversByOnDay(LoginMember member, LocalDate onDay) {
        return coverWorkDaoRepository.getStaffAcceptedCoversByOnDay(member.getMemberId(), onDay);
    }

    private List<WorkTime> getStaffWorksByOnDay(LoginMember member, LocalDate onDay) {
        return workTimeDaoRepository.getStaffWorksByOnDay(member.getMemberId(), onDay);
    }
}
