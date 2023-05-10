package com.example.sidepot.work.dao;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.example.sidepot.work.dao.CoverWorkNoticeResDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

import static com.example.sidepot.store.domain.QStore.store;
import static com.example.sidepot.member.domain.QStaff.staff;
import static com.example.sidepot.employment.domain.QEmployment.employment;
import static com.example.sidepot.work.domain.QCoverWorkNotice.coverWorkNotice;
import static com.example.sidepot.work.domain.QCoverWork.coverWork;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.set;


@RequiredArgsConstructor
@Repository
public class CWNotificationReadQuery {

    private final JPAQueryFactory jpaQueryFactory;

    @Transactional(readOnly = true)
    public List<CoverNoticeResDto> readCoverWorkNotice(Long staffId){
        List<Long> storeIds = jpaQueryFactory.query()
                .select(store.storeId)
                .from(employment)
                .leftJoin(employment.staff, staff)
                .leftJoin(employment.store, store)
                .where(employment.staff.memberId.eq(staffId))
                .distinct()
                .fetch();

        Map<Long, CoverNoticeResDto> resultMap = jpaQueryFactory.query()
                .from(coverWorkNotice)
                .leftJoin(store).on(coverWorkNotice.storeId.eq(store.storeId))
                .leftJoin(staff).on(coverWorkNotice.staffId.eq(staff.memberId))
                .leftJoin(coverWorkNotice.coverWorkList, coverWork)
                .where(coverWorkNotice.storeId.in(storeIds))
                .orderBy(coverWorkNotice.createDt.asc())
                .transform(groupBy(coverWorkNotice.coverWorkNoticeId).as(Projections.constructor(
                        CoverNoticeResDto.class,
                        coverWorkNotice.coverWorkNoticeId,
                        coverWorkNotice.coverNoticeType,
                        coverWorkNotice.createDt,
                        coverWorkNotice.readStatus,
                        coverWorkNotice.isAccepted,
                        store.storeId,
                        store.branchName,
                        staff.memberId,
                        staff.memberName,
                        set(Projections.constructor(CoverWorkDto.class,
                                coverWork.coverWorkId,
                                coverWork.coverDate,
                                coverWork.startTime,
                                coverWork.endTime).as("coverWorkDtoList")))));
        return resultMap.keySet().stream()
                .map((resultMap::get))
                .collect(Collectors.toList());
    }
}
