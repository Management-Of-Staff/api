package com.example.sidepot.command.attendance.repository;

import com.example.sidepot.command.attendance.domain.CoverAttendance;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static  com.example.sidepot.command.attendance.domain.QCoverAttendance.coverAttendance;

@RequiredArgsConstructor
@Repository
public class CoverAttendanceDaoRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<CoverAttendance> readCoverAttendanceToday(Long staffId, LocalDate onDay){
        return jpaQueryFactory.selectFrom(coverAttendance)
                .where(coverAttendance.workerId.workerId.eq(staffId)
                        .and(coverAttendance.workDateTime.workDate.eq(onDay)))
                .fetch();
    }

    public Optional<CoverAttendance> findCttByCwIdOnDay(Long cwId, LocalDate onDay){
        CoverAttendance coverAttendancePs = jpaQueryFactory
                .selectFrom(coverAttendance)
                .where(coverAttendance.coverWorkId.eq(cwId)
                        .and(coverAttendance.workDateTime.workDate.eq(onDay)))
                .fetchOne();
        return Optional.ofNullable(coverAttendancePs);
    }

}