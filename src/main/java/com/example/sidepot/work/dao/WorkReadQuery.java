package com.example.sidepot.work.dao;

import com.example.sidepot.work.domain.WorkTime;
import com.example.sidepot.work.dto.ReadWorkResponseDto.*;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.sidepot.employment.domain.QEmployment.employment;
import static com.example.sidepot.work.domain.QWorkTime.workTime;
import static com.example.sidepot.member.domain.QStaff.staff;
import static com.example.sidepot.store.domain.QStore.store;
import static com.example.sidepot.work.domain.QCoverWork.coverWork;

@RequiredArgsConstructor
@Repository
public class WorkReadQuery {
    private final JPAQueryFactory jpaQueryFactory;

    @Transactional(readOnly = true)
    public Map<List<Serializable>, List<StaffWork>> readAllEmployment(Long storeId){
        List<StaffWork> fetch = jpaQueryFactory.query()
                .select(Projections.constructor(
                        StaffWork.class,
                        employment.employmentId,
                        staff.memberName,
                        staff.profileImage.fileSavePath,
                        workTime.dayOfWeek,
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
    public List<WorkTime> readAllEmploymentOnDay(Long ownerId, Long storeId, LocalDate onDay) {
        return jpaQueryFactory.query()
                .select(workTime)
                .from(workTime)
                .leftJoin(workTime.store, store).fetchJoin()
                .leftJoin(workTime.staff, staff).fetchJoin()
                .leftJoin(workTime.coverWorkList, coverWork).fetchJoin()
                .where(workTime.store.storeId.eq(storeId)
                        .and(workTime.dayOfWeek.eq(onDay.getDayOfWeek())))
                .fetch();

    }

    @Transactional(readOnly = true)
    public List<ReadWorkByStoreResDto> readAllWorkOfStaff(Long staffId){
        return jpaQueryFactory.query()
                .select(Projections.constructor(
                        ReadWorkByStoreResDto.class,
                        store.storeId,
                        store.branchName,
                        store.storeName,
                        workTime.workTimeId,
                        workTime.startTime,
                        workTime.endTime,
                        workTime.dayOfWeek
                ))
                .from(workTime)
                .join(workTime.store, store)
                .where(workTime.staff.memberId.eq(staffId))
                .fetch();
    }
    @Transactional(readOnly = true)
    public List<WorkTime> readAllCoverWorkOfStaff(Long staffId){
        return jpaQueryFactory.query()
                .select(workTime)
                .from(workTime)
                .leftJoin(workTime.store, store).fetchJoin()
                .leftJoin(workTime.staff, staff).fetchJoin()
                .leftJoin(workTime.coverWorkList, coverWork).fetchJoin()
                .fetch();
    }
}
