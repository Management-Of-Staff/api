package com.example.sidepot.command.work.repository.query;

import com.example.sidepot.command.work.domain.CoverManager;
import com.example.sidepot.command.work.domain.CoverWork;
import com.example.sidepot.command.work.dto.CoverWorkResDto;
import com.example.sidepot.command.work.dto.StaffCoverSchedule;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import static com.example.sidepot.command.work.domain.QCoverWork.coverWork;
import static com.example.sidepot.command.work.domain.QCoverManager.coverManager;

@RequiredArgsConstructor
@Repository
public class CoverWorkDaoRepository {

    private final JPAQueryFactory jpaQueryFactory;


    /**
     * 날짜로 수락한 대타가 있는지 판단한다. - 스펙으로 동적쿼리 범용으로 써야댐
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


    public List<CoverWork> findCoverAfterDayWithWorkId(Long wtId, LocalDate onDay){
        return jpaQueryFactory
                .selectFrom(coverWork)
                .where(coverWork.workTime.workTimeId.eq(wtId)
                        .and(coverWork.coverDateTime.coverDate.after(onDay)),
                        eqIsAccepted(true))
                .fetch();
    }

    /**
     * 현재는 wt에 대해 cw 는 1:N 관계이다. 하지만, 특정 날짜로 조회하면 1:1 관계를 기대한다.
     * 추후 특정 날짜도 1:N 관계가 될 수 있다. 그때는 시간까지 포함해야한다.
     */
    public Optional<CoverWork> findCwByWtIdOnDay(Long wtId, LocalDate onDay){
        CoverWork coverWorkPs = jpaQueryFactory
                .selectFrom(coverWork)
                .where(coverWork.workTime.workTimeId.eq(wtId)
                                .and(coverWork.coverDateTime.coverDate.eq(onDay)),
                        eqIsAccepted(true))
                .fetchOne();
        return Optional.ofNullable(coverWorkPs);
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
