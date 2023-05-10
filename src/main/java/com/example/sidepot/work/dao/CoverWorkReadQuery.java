package com.example.sidepot.work.dao;

import com.example.sidepot.member.domain.QStaff;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.sidepot.work.domain.QCoverWork.coverWork;
import static com.example.sidepot.member.domain.QStaff.staff;
import static com.example.sidepot.store.domain.QStore.store;

@RequiredArgsConstructor
@Repository
public class CoverWorkReadQuery {

    private final JPAQueryFactory jpaQueryFactory;

    @Transactional(readOnly = true)
    public List<CoverWorkNotificationResDto> readMyCoverWorkAccepted(Long staffId){
        QStaff requestedStaff = staff;
        return jpaQueryFactory.query()
                .select(Projections.constructor(
                        CoverWorkNotificationResDto.class,
                        coverWork.workingStoreId,
                        coverWork.workingStoreName,
                        requestedStaff.memberId,
                        requestedStaff.memberName,
                        coverWork.coverDate,
                        coverWork.startTime,
                        coverWork.endTime
                ))
                .from(coverWork)
                .leftJoin(requestedStaff).on(requestedStaff.memberId.eq(coverWork.requestedStaffId))
                .where(coverWork.acceptedStaffId.eq(staffId))
                .orderBy(coverWork.coverDate.asc())
                .fetch();
    }

    @Transactional(readOnly = true)
    public List<CoverWorkNotificationResDto> readMyCoverWorkAcceptedNEW(Long staffId){
        QStaff requestedStaff = staff;
        return jpaQueryFactory.query()
                .select(Projections.constructor(
                        CoverWorkNotificationResDto.class,
                        coverWork.workingStoreId,
                        coverWork.workingStoreName,
                        requestedStaff.memberId,
                        requestedStaff.memberName,
                        coverWork.coverDate,
                        coverWork.startTime,
                        coverWork.endTime
                ))
                .from(coverWork)
                .leftJoin(requestedStaff).on(requestedStaff.memberId.eq(coverWork.requestedStaffId))
                .where(coverWork.acceptedStaffId.eq(staffId))
                .orderBy(coverWork.coverDate.asc())
                .groupBy(coverWork.coverWorkNotice.coverWorkNoticeId)
                .fetch();
    }

    @Transactional(readOnly = true)
    public List<CoverWorkNotificationResDto> readMyCoverWorkRequested(Long staffId){
        QStaff acceptedStaff = staff;
        return jpaQueryFactory.query()
                .select(Projections.constructor(
                        CoverWorkNotificationResDto.class,
                        coverWork.isAccepted,
                        coverWork.workingStoreId,
                        coverWork.workingStoreName,
                        acceptedStaff.memberId,
                        acceptedStaff.memberName,
                        coverWork.coverDate,
                        coverWork.startTime,
                        coverWork.endTime
                ))
                .from(coverWork)
                .leftJoin(acceptedStaff).on(acceptedStaff.memberId.eq(coverWork.acceptedStaffId))
                .where(coverWork.requestedStaffId.eq(staffId))
                .orderBy(coverWork.coverDate.asc())
                .groupBy(coverWork.coverWorkNotice.coverWorkNoticeId)
                .distinct()
                .fetch();
    }
}
