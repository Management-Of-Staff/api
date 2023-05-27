package com.example.sidepot.command.attendance.app;

import com.example.sidepot.command.attendance.domain.Attendance;
import com.example.sidepot.command.attendance.domain.CoverAttendance;
import com.example.sidepot.command.attendance.repository.AttendanceRepository;
import com.example.sidepot.command.attendance.repository.CoverAttendanceRepository;
import com.example.sidepot.command.work.repository.CoverWorkRepository;
import com.example.sidepot.global.security.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CheckOutService {

    private final CoverWorkRepository coverWorkRepository;
    private final CoverAttendanceRepository coverAttendanceRepository;
    private final AttendanceRepository attendanceRepository;

    @Transactional
    public void checkOutWork(LoginMember member, Long workTimeId, LocalDateTime now){
        //어댑터 : 출첵하려는 근무가 있는지?

        //근무 ID로 오늘 날짜의 출근정보 조회
        Optional<Attendance> attendanceOp = attendanceRepository.findByWorkTimeIdAndWorkDateTime_DayOfWeek(workTimeId, now.toLocalDate().getDayOfWeek());
        Attendance attendancePs = attendanceOp.orElseThrow();

        //검증 및 출근 처리
        attendancePs.checkOutWork(now, 10L);
    }

    @Transactional
    public void checkOutCoverWork(LoginMember member, Long coverWorkId, LocalDateTime now){
        //어댑터 : 출첵하려는 대타가 있는가?

        //대타 ID로 오늘 날짜의 출근 정보를 조회
        Optional<CoverAttendance> attendanceOp = coverAttendanceRepository.findByCoverWorkIdAndWorkDateTime_WorkDate(coverWorkId, now.toLocalDate());
        CoverAttendance coverAttendancePs = attendanceOp.orElseThrow();

        //검증 및 출근 처리
        coverAttendancePs.checkOutCoverWork(now, 10L);
    }
}
