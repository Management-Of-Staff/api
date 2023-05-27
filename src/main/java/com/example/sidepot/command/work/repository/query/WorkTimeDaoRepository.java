package com.example.sidepot.command.work.repository.query;


import com.example.sidepot.command.work.domain.WorkTime;
import com.example.sidepot.command.work.dto.StaffWorkScheduleResDto;
import com.example.sidepot.command.work.dto.StoreWorkerResDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.sidepot.command.work.domain.QWorkTime.workTime;
import static com.example.sidepot.command.work.domain.QCoverWork.coverWork;
import static com.example.sidepot.command.member.domain.QStaff.staff;
import static com.example.sidepot.command.store.domain.QStore.store;
import static com.querydsl.core.types.Projections.list;

@RequiredArgsConstructor
@Repository
public class WorkTimeDaoRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /**  안쓰는 클래스
     * SELECT *
     * FROM work_time wt
     * LEFT JOIN cover_work cw ON wt.work_time_id = cw.work_time_id AND cw.cover_date = '2023.05.22'
     * WHERE cw.work_time_id IS NULL and wt.day_of_week = 'MONDAY' and wt.staff_id = 1;
     * <p>
     * 특정 요일(날짜)에 근무와 대타를 조회한다.
     * 만족하는 대타가 있으면 해당 근무는 출/퇴근에서 없는 근무라고 판단한다.
     */
    @Deprecated
    @Transactional(readOnly = true)
    public List<WorkTime> getStaffWorksByOnDay(Long staffId, LocalDate onDay) {
        return jpaQueryFactory
                .selectFrom(workTime)
                .leftJoin(coverWork)
                .on(workTime.workTimeId.eq(coverWork.workTime.workTimeId)
                        .and(coverWork.coverDateTime.coverDate.eq(onDay)))
                .where(eqStaffId(staffId),
                        eqIsDeleted(false),
                        eqDayOfWeek(onDay.getDayOfWeek())
                                .and(coverWork.workTime.isNull()))
                .fetch();
    }

    /**
     * 매장의 전체 근무를 성사된 대타와 함께 조회한다.
     * 특정 날짜에 대해 근무와 대타는 1:1 관계가 기대되기 때문에 JPA 즉시로딩 방식과 비슷하다.
     * 다만 특정 날짜에 대해 근무와 대타가 1:N 관계가 아니면 수정해야한다.
     */
    @Transactional(readOnly = true)
    public List<StoreWorkerResDto> getStoreWorkToday(Long storeId, LocalDate today) {
        return jpaQueryFactory
                .select(Projections.constructor(StoreWorkerResDto.class,
                        workTime.staff.memberId,
                        workTime.staff.memberName,
                        workTime.startTime,
                        workTime.endTime,
                        list(Projections.constructor(StoreWorkerResDto.RequestedCoverStaff.class,
                                coverWork.acceptedStaff.id,
                                coverWork.acceptedStaff.name,
                                coverWork.coverDateTime.startTime,
                                coverWork.coverDateTime.endTime))))
                .from(workTime)
                .leftJoin(workTime.staff, staff) //쩔수 조인 - employment-staff 매핑
                .leftJoin(coverWork).on(workTime.workTimeId.eq(coverWork.workTime.workTimeId)
                        .and(coverWork.coverDateTime.coverDate.eq(today))
                        .and(coverWork.isAccepted.eq(true)))
                .where(eqIsDeleted(false), eqStoreId(storeId), eqDayOfWeek(today.getDayOfWeek()))
                .fetch();
    }

    @Transactional(readOnly = true)
    public List<StaffWorkScheduleResDto> getStaffsWorkByYearMonth(Long staffId, LocalDate firstDay, LocalDate lastDay) {
        return jpaQueryFactory
                .select(Projections.constructor(StaffWorkScheduleResDto.class,
                        workTime.workTimeId,
                        workTime.staff.memberId,
                        workTime.staff.memberName,
                        workTime.store.storeId,
                        workTime.store.branchName,
                        workTime.store.storeName,
                        workTime.dayOfWeek,
                        workTime.startTime,
                        workTime.endTime,
                        list(Projections.constructor(StaffWorkScheduleResDto.CoverSchedule.class,
                                coverWork.coverWorkId,
                                coverWork.acceptedStaff.id,
                                coverWork.acceptedStaff.name,
                                coverWork.coverDateTime.coverDate,
                                coverWork.coverDateTime.startTime,
                                coverWork.coverDateTime.endTime))))
                .from(workTime)
                .leftJoin(workTime.store, store) //쩔수 조인 - employment-store 매핑
                .leftJoin(workTime.staff, staff) //쩔수 조인 - employment-staff 매핑
                .leftJoin(coverWork)
                    .on(workTime.workTimeId.eq(coverWork.workTime.workTimeId)
                        .and(coverWork.coverDateTime.coverDate.between(firstDay, lastDay)))
                .where(eqStaffId(staffId), eqIsDeleted(false))
                .fetch();

    }

    @Transactional(readOnly = true)
    public Map<Object[], List<WorkTime>> getAllWorkTimeByStoreId(Long storeId, LocalDate onDay){
        List<WorkTime> fetch = jpaQueryFactory
                .select(workTime)
                .from(workTime)
                .join(workTime.store, store)
                .where(store.storeId.eq(storeId), workTime.dayOfWeek.eq(onDay.getDayOfWeek()))

                .fetch();

        return fetch.stream()
                .collect(Collectors.groupingBy(wt -> Arrays.asList(wt.getStaff(), wt.getStartTime(), wt.getEndTime()).toArray()));
    }

    public List<WorkTime> findWtByStaff(Long staffId){
        return jpaQueryFactory.selectFrom(workTime)
                .where(workTime.staff.memberId.eq(staffId)
                        .and(workTime.isDeleted.eq(false)))
                .fetch();
    }


    private BooleanExpression eqStaffId(final Long staffId) {
        return staffId != null ? workTime.staff.memberId.eq(staffId) : null;
    }

    private BooleanExpression eqStoreId(final Long storeId) {
        return storeId != null ? workTime.store.storeId.eq(storeId) : null;
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
