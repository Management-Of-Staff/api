package com.example.sidepot.attendance.app;

import com.example.sidepot.attendance.domain.AttendanceStatus;
import com.example.sidepot.attendance.domain.CoverAttendance;
import com.example.sidepot.attendance.domain.WorkerId;
import com.example.sidepot.attendance.repository.CoverAttendanceRepository;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.work.domain.CoverWork;
import com.example.sidepot.work.repository.CoverWorkRepository;
import com.example.sidepot.work.repository.WorkTimeRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class CheckInService {

    private final CoverWorkRepository coverWorkRepository;
    private final CoverAttendanceRepository coverAttendanceRepository;
    private final WorkTimeRepository workTimeRepository;

    @Transactional
    public void checkInCoverWork(LoginMember member, Long coverWorkId, LocalDateTime now){
        //어댑터
        CoverWork coverWorkPs = coverWorkRepository.findById(coverWorkId).orElseThrow();

        AttendanceStatus attendanceStatus = coverWorkPs.checkIn(now, 10L);

        CoverAttendance coverAttendance = new CoverAttendance(
                coverWorkPs.getCoverManager().getStoreId(),
                new WorkerId(member.getMemberId(), member.getMemberName()),
                now,
                attendanceStatus);

        coverAttendanceRepository.save(coverAttendance);
    }

    @Transactional
    public void checkInWork(LoginMember member, Long workTimeId, LocalDateTime now){
        workTimeRepository.findById(workTimeId).orElseThrow();
    }
}
