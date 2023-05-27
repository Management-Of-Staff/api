package com.example.sidepot.command.employment.query;

import com.example.sidepot.command.employment.dto.EmploymentReadDto.ReadOneEmploymentResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

import static com.example.sidepot.command.employment.domain.QEmployment.employment;
import static com.example.sidepot.command.store.domain.QStore.store;
import static com.example.sidepot.command.member.domain.QStaff.staff;

@RequiredArgsConstructor
@Repository
public class EmploymentDaoRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public ReadOneEmploymentResponse findById(Long empId) {
        return (ReadOneEmploymentResponse) jpaQueryFactory
                .select(Projections.constructor(
                        ReadOneEmploymentResponse.class,
                        employment.employmentId,
                        staff.memberId,
                        staff.memberName,
                        staff.memberPhoneNum,
                        staff.profileImage.fileSavePath,
                        employment.rank,
                        employment.hourlyWage,
                        employment.healthCertificate))
                .from(employment)
                .join(employment.store, store).fetchJoin()
                .join(employment.staff, staff).fetchJoin()
                .where(employment.employmentId.eq(empId)
                        .and(employment.isDeleted.eq(false)))
                .fetch();
    }
}
