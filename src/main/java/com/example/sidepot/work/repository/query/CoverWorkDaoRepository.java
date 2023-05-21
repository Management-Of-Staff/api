package com.example.sidepot.work.repository.query;

import com.example.sidepot.work.domain.CoverWork;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;


import static com.example.sidepot.work.domain.QCoverWork.coverWork;

@RequiredArgsConstructor
@Repository
public class CoverWorkDaoRepository {

    private final JPAQueryFactory jpaQueryFactory;


    /**
     * 수락한 대타가 있는지 판단한다.
     */
    public List<CoverWork> getStaffAcceptedCoversByOnDay(Long staffId, LocalDate today) {
        return jpaQueryFactory
                .selectFrom(coverWork)
                .where(
                        eqAcceptedStaff(staffId),
                        eqIsAccepted(true),
                        eqCoverDate(today))
                .fetch();
    }

    private BooleanExpression eqCoverDate(final LocalDate coverDate) {
        if (coverDate != null) {
            return coverWork.coverDateTime.coverDate.eq(coverDate);
        }
        return null;
    }

    private BooleanExpression eqAcceptedStaff(final Long acceptedStaffId) {
        if (acceptedStaffId == null) return null;
        return coverWork.acceptedStaff.acceptedStaffId.eq(acceptedStaffId);
    }

    private BooleanExpression eqIsAccepted(final boolean isAccepted) {
        return coverWork.isAccepted.eq(isAccepted);
    }
}
