package com.example.sidepot.command.attendance.repository;

import com.example.sidepot.command.attendance.domain.*;
import com.example.sidepot.command.attendance.dto.AttendanceTodayResDto;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static  com.example.sidepot.command.attendance.domain.QAttendance.attendance;
import static com.example.sidepot.command.attendance.domain.QCoverAttendance.coverAttendance;

@RequiredArgsConstructor
@Repository
public class AttendanceDaoRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public AttendanceTodayResDto readAttendanceToday(Long staffId, LocalDate onDay){
        List<Attendance> attendanceList = jpaQueryFactory.selectFrom(attendance)
                .where(attendance.workDateTime.workDate.eq(onDay)
                        .and(attendance.workerId.workerId.eq(staffId)))
                .fetch();

        List<CoverAttendance> coverAttendanceList = jpaQueryFactory.selectFrom(coverAttendance)
                .where(coverAttendance.workerId.workerId.eq(staffId)
                        .and(coverAttendance.workDateTime.workDate.eq(onDay)))
                .fetch();

        return new AttendanceTodayResDto(attendanceList, coverAttendanceList);
    }

    public List<Tuple> readAllAttendanceTodayByWorkId(List<Long> workTimeId){
        List<Tuple> attendanceStatusWithId = jpaQueryFactory.select(attendance.attendanceStatus, attendance.workerId.workerId)
                .from(attendance)
                .where(attendance.workTimeId.in(workTimeId))
                .fetch();
        return attendanceStatusWithId;
    }

    public List<Tuple> readAllCoverAttendanceTodayByCoverId(List<Long> coverWorkId){
        List<Tuple> coverAttendanceStatusWithId = jpaQueryFactory.select(coverAttendance.attendanceStatus, attendance.workerId.workerId)
                .from(coverAttendance)
                .where(coverAttendance.coverWorkId.in(coverWorkId))
                .fetch();
        return coverAttendanceStatusWithId;
    }
}
