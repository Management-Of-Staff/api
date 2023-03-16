package com.example.sidepot.work.dao;

import com.example.sidepot.work.domain.WorkTime;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.sidepot.work.domain.QEmployment.employment;
import static com.example.sidepot.work.domain.QWorkTime.workTime;
import static com.example.sidepot.member.domain.QStaff.staff;
import static com.example.sidepot.store.domain.QStore.store;

@RequiredArgsConstructor
@Repository
public class WorkReedQuery {
    private final JPAQueryFactory jpaQueryFactory;

    @Transactional(readOnly = true)
    public Map<List<Serializable>, List<StaffWork>> readAllEmployment(Long storeId){
        List<StaffWork> fetch = jpaQueryFactory.query()
                .select(Projections.constructor(
                        StaffWork.class,
                        employment.employmentId,
                        staff.memberName,
                        staff.profileImage.fileOriginName,
                        workTime.day,
                        workTime.startTime,
                        workTime.endTime,
                        employment.attendanceStatus))
                .from(employment)
                .leftJoin(employment.workTimeList, workTime)
                .leftJoin(employment.staff, staff)
                .leftJoin(employment.store, store)
                .where(store.storeId.eq(storeId)
                        .and(employment.withdrawal_status.eq(false)))
                .fetch();

        return fetch.stream().collect(Collectors.groupingBy(
                o -> Arrays.asList(o.getEmploymentId(), o.getStartTime(), o.getEndTime())));
    }

    @Transactional(readOnly = true)
    public List<StaffWorkOnDay> readAllEmploymentOnDay(Long ownerId, Long storeId, DayOfWeek day){
        return jpaQueryFactory.query()
                .select(Projections.constructor(
                                StaffWorkOnDay.class,
                                employment.employmentId,
                                staff.memberName,
                                workTime.day,
                                workTime.startTime,
                                workTime.endTime))
                .from(employment)
                .join(employment.workTimeList, workTime)
                .join(employment.staff, staff)
                .join(employment.store, store)
                .where(store.storeId.eq(storeId)
                        .and(workTime.day.eq(day))
                        .and(employment.withdrawal_status.eq(false)))
                .fetch();
    }
    @Transactional(readOnly = true)
    public List<WorkTime> readAllEmploymentInTime(Long staffId, Set<DayOfWeek> days,
                                                  LocalTime startTime, LocalTime endTime){
        return jpaQueryFactory.query().select(workTime)
                .from(workTime)
                .join(workTime.employment, employment)
                .join(employment.staff, staff)
                .where(timeRangePredicate(startTime, endTime)
                        .and(weekdayPredicate(days))
                        .and(staff.memberId.eq(staffId)))
                .fetch();
    }

    BooleanExpression timeRangePredicate(LocalTime startTime, LocalTime endTime){
        return workTime.startTime.between(startTime, endTime)
                .or(workTime.endTime.between(startTime,endTime));
    }

    BooleanExpression weekdayPredicate(Set<DayOfWeek> days){
        return workTime.day.in(days);
    }
}
