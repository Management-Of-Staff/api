package com.example.sidepot.command.attendance.app;

import com.example.sidepot.command.attendance.domain.Attendance;
import com.example.sidepot.command.attendance.domain.AttendanceStatus;
import com.example.sidepot.command.attendance.domain.CoverAttendance;
import com.example.sidepot.command.attendance.domain.WorkerId;
import com.example.sidepot.command.attendance.repository.AttendanceRepository;
import com.example.sidepot.command.attendance.repository.CoverAttendanceRepository;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.command.work.domain.CoverWork;
import com.example.sidepot.command.work.repository.CoverWorkRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CheckInService {

    private final CoverWorkRepository coverWorkRepository;
    private final CoverAttendanceRepository coverAttendanceRepository;
    private final AttendanceRepository attendanceRepository;
    @Transactional
    public void checkInWork(LoginMember member, Long workTimeId, LocalDateTime now){
        //어댑터 : 출첵하려는 근무가 있는지?

        //근무 ID로 오늘 날짜의 출근정보 조회
        Optional<Attendance> attendanceOp = attendanceRepository.findByWorkTimeIdAndWorkDateTime_DayOfWeek(workTimeId, now.toLocalDate().getDayOfWeek());
        Attendance attendancePs = attendanceOp.orElseThrow();

        //검증 및 출근 처리
        attendancePs.checkInCWork(now, 10L);
    }
    @Transactional
    public void checkInCoverWork(LoginMember member, Long coverWorkId, LocalDateTime now){
        //어댑터 : 출첵하려는 대타가 있는가?

        //대타 ID로 오늘 날짜의 출근 정보를 조회
        Optional<CoverAttendance> attendanceOp = coverAttendanceRepository.findByCoverWorkIdAndWorkDateTime_WorkDate(coverWorkId, now.toLocalDate());
        CoverAttendance coverAttendancePs = attendanceOp.orElseThrow();

        //검증 및 출근 처리
        coverAttendancePs.checkInCoverWork(now, 10L);
    }

    @Deprecated
    @Transactional
    public void checkInCoverWorkLegacy(LoginMember member, Long coverWorkId, LocalDateTime now){
        CoverWork coverWorkPs = coverWorkRepository.findById(coverWorkId).orElseThrow();

        AttendanceStatus attendanceStatus = coverWorkPs.checkIn(now, 10L);

        CoverAttendance coverAttendance = new CoverAttendance(
                coverWorkPs.getCoverManager().getStoreInfo(),
                new WorkerId(member.getMemberId(), member.getMemberName()),
                now,
                attendanceStatus);

        coverAttendanceRepository.save(coverAttendance);
    }
}
