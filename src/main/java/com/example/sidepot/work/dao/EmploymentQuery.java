package com.example.sidepot.work.dao;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

import static com.example.sidepot.work.domain.QEmployment.employment;
import static com.example.sidepot.work.domain.QWeekWorkTime.weekWorkTime;
import static com.example.sidepot.member.domain.QStaff.staff;
@RequiredArgsConstructor
@Repository
public class EmploymentQuery {
    private final JPAQueryFactory jpaQueryFactory;

    public List<EmploymentWorkList> readAllEmployment(Long ownerId, Long storeId){
        return jpaQueryFactory.query()
                .select(Projections.constructor(
                                EmploymentWorkList.class,
                                employment.employmentId,
                                staff.memberName,
                                staff.profileImage.fileOriginName,
                                weekWorkTime.day,
                                weekWorkTime.startTime,
                                weekWorkTime.endTime,
                                employment.attendanceStatus))
                .from(employment)
                .leftJoin(employment.weekWorkTimeList, weekWorkTime)
                .leftJoin(employment.staff, staff)
                .where(employment.store.owner.memberId.eq(ownerId)
                        .and(employment.store.storeId.eq(storeId)))
                .fetch();
    }

    public List<Long> readAllEmploymentOnDay(Long ownerId, Long storeId){
        return Collections.singletonList(1l);
    }
}
