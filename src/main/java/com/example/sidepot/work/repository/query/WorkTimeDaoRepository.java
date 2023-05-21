package com.example.sidepot.work.repository.query;


import com.example.sidepot.work.domain.WorkTime;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import static com.example.sidepot.work.domain.QWorkTime.workTime;
import static com.example.sidepot.work.domain.QCoverWork.coverWork;

@RequiredArgsConstructor
@Repository
public class WorkTimeDaoRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * SELECT *
     * FROM work_time wt
     * LEFT JOIN cover_work cw ON wt.work_time_id = cw.work_time_id AND cw.cover_date = '2023.05.22'
     * WHERE cw.work_time_id IS NULL and wt.day_of_week = 'MONDAY' and wt.staff_id = 1;
     * <p>
     * 특정 요일(날짜)에 근무와 대타를 조회한다.
     * 만족하는 대타가 있으면 해당 근무는 출/퇴근에서 없는 근무라고 판단한다.
     */
    public List<WorkTime> getStaffWorksByOnDay(Long staffId, LocalDate onDay) {
        return jpaQueryFactory
                .selectFrom(workTime)
                .leftJoin(coverWork)
                .on(workTime.workTimeId.eq(coverWork.workTIme.workTimeId)
                        .and(coverWork.coverDateTime.coverDate.eq(onDay)))
                .where(eqStaffId(staffId),
                        eqIsDeleted(false),
                        eqDayOfWeek(onDay.getDayOfWeek())
                                .and(coverWork.workTIme.isNull()))
                .fetch();
    }

    private BooleanExpression eqStaffId(final Long staffId) {
        return staffId != null ? workTime.staff.memberId.eq(staffId) : null;
    }


    private BooleanExpression eqDayOfWeek(final DayOfWeek day) {
        if (day != null) {
            return workTime.dayOfWeek.eq(day);
        }
        return null;
    }

    private BooleanExpression eqIsDeleted(final boolean isDeleted) {
        return workTime.isDeleted.eq(isDeleted);
    }
}
