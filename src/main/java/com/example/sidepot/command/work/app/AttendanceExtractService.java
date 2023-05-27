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


/**
 *  안쓰는 클래스
 */
@Deprecated
@RequiredArgsConstructor
@DomainService
public class AttendanceExtractService {

    private final WorkTimeDaoRepository workTimeDaoRepository;
    private final CoverWorkDaoRepository coverWorkDaoRepository;

    @Transactional(readOnly = true)
    public AttendanceTodayResDto extractWorkScheduleOnDay(LoginMember member, LocalDate onDay){

        List<WorkTime> staffWorksByOnDay = getStaffWorksByOnDay(member, onDay);
        List<CoverWork> staffAcceptedCoversByOnDay = getStaffAcceptedCoversByOnDay(member, onDay);

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
