package com.example.sidepot.work.repository.query;

import com.example.sidepot.work.domain.CoverManager;
import com.example.sidepot.work.domain.CoverWork;
import com.example.sidepot.work.dto.StaffCoverSchedule;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


import static com.example.sidepot.work.domain.QCoverWork.coverWork;
import static com.example.sidepot.work.domain.QCoverManager.coverManager;

@RequiredArgsConstructor
@Repository
public class CoverWorkDaoRepository {

    private final JPAQueryFactory jpaQueryFactory;


    /**
     * 수락한 대타가 있는지 판단한다.
     */
    @Transactional(readOnly = true)
    public List<CoverWork> getStaffAcceptedCoversByOnDay(Long staffId, LocalDate today) {
        return jpaQueryFactory
                .selectFrom(coverWork)
                .where(
                        eqAcceptedStaff(staffId),
                        eqIsAccepted(true),
                        eqCoverDate(today))
                .fetch();
    }


    /**
     * 직원의 이번달 수락된 대타(추가근무)를 조회한다.
     */
    @Transactional(readOnly = true)
    public List<StaffCoverSchedule> getStaffAcceptedCoverByYearMonth(Long staffId, LocalDate firstDay, LocalDate lastDay) {
        return jpaQueryFactory
                .select(Projections.constructor(StaffCoverSchedule.class,
                        coverWork.coverWorkId,
                        coverManager.storeInfo.storeId,
                        coverManager.storeInfo.branchName,
                        coverManager.storeInfo.storeName,
                        coverWork.coverDateTime.coverDate,
                        coverWork.coverDateTime.startTime,
                        coverWork.coverDateTime.endTime))
                .from(coverWork)
                .leftJoin(coverManager).on(coverWork.coverManager.id.eq(coverManager.id))
                .where(eqAcceptedStaff(staffId), eqIsAccepted(true)
                        .and(coverWork.coverDateTime.coverDate.between(firstDay, lastDay)))
                .fetch();
    }

    /**
     * 알림함에서 요청 또는 수락된 대타 근무의 상세보기
     */
    public List<CoverWorkResDto.CoverWorkByNoticeResDto> getCoverDetailsOfNoticeBox(Long coverManageId) {
        return jpaQueryFactory
                .select(Projections.constructor(CoverWorkResDto.CoverWorkByNoticeResDto.class,
                        coverWork.coverWorkId,
                        coverWork.coverDateTime))
                .from(coverWork)
                .where(coverWork.coverManager.id.eq(coverManageId))
                .fetch();
    }

    /**
     *
     */
    public List<CoverWorkResDto.RequestedCoverWorkResDto> readRequestedCoverWork(Long staffId){
        List<CoverManager> fetch = jpaQueryFactory
                .selectFrom(coverManager)
                .join(coverManager.coverWorkList, coverWork).fetchJoin()
                .where(coverManager.requestedStaff.id.eq(staffId))
                .distinct()
                .fetch();

        return fetch.stream()
                .map(cm -> new CoverWorkResDto.RequestedCoverWorkResDto(cm, cm.getCoverWorkList()))
                .collect(Collectors.toList());
    }

    public void readAcceptedCoverWork(Long staffId){

    }


    private BooleanExpression eqCoverDate(final LocalDate coverDate) {
        if (coverDate != null) {
            return coverWork.coverDateTime.coverDate.eq(coverDate);
        }
        return null;
    }

    private BooleanExpression eqAcceptedStaff(final Long acceptedStaffId) {
        if (acceptedStaffId == null) return null;
        return coverWork.acceptedStaff.id.eq(acceptedStaffId);
    }

    private BooleanExpression eqIsAccepted(final boolean isAccepted) {
        return coverWork.isAccepted.eq(isAccepted);
    }
}
