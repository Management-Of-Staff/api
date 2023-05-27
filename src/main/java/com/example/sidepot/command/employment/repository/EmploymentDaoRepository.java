package com.example.sidepot.command.employment.repository;

import com.example.sidepot.command.employment.dto.EmploymentReadDto.ReadOneEmploymentResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.example.sidepot.command.employment.domain.QEmployment.employment;
import static com.example.sidepot.command.store.domain.QStore.store;
import static com.example.sidepot.command.member.domain.QStaff.staff;

@RequiredArgsConstructor
@Repository
public class EmploymentDaoRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Optional<ReadOneEmploymentResponse> findById(Long empId) {
        ReadOneEmploymentResponse employmentResponseOp = jpaQueryFactory
                .select(Projections.constructor(
                        ReadOneEmploymentResponse.class,
                        employment.employmentId,
                        staff.memberId,
                        staff.memberName,
                        staff.memberPhoneNum,
                        staff.profileImage,
                        employment.rank,
                        employment.hourlyWage,
                        employment.healthCertificate))
                .from(employment)
                .join(employment.store, store)
                .join(employment.staff, staff)
                .where(employment.employmentId.eq(empId)
                        .and(employment.isDeleted.eq(false)))
                .fetchOne();
        return Optional.ofNullable(employmentResponseOp);
    }

    public boolean existsByStaffAndStore(Long staffId, Long storeId){
        return jpaQueryFactory.selectFrom(employment)
                .where(employment.staff.memberId.eq(staffId)
                        .and(employment.store.storeId.eq(storeId))
                        .and(employment.isDeleted.eq(false)))
                .fetchFirst() != null;
    }
}
