package com.example.sidepot.command.notification.work.repository;

import com.example.sidepot.command.notification.work.domain.StaffNotice;
import com.example.sidepot.command.notification.work.dto.CoverNoticeResDto.CoverNoticeBoxResDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.sidepot.command.notification.work.domain.QStaffNotice.staffNotice;

@RequiredArgsConstructor
@Repository
public class StaffNoticeSpecRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public StaffNotice getOneStaffNotice(Long staffId){
        return jpaQueryFactory
                .selectFrom(staffNotice)
                .where(staffNotice.receiver.receiverId.eq(staffId)
                        .and(staffNotice.isDeleted.eq(false))
                        .and(staffNotice.isRead.eq(false)))
                .orderBy(staffNotice.createDt.desc())
                .limit(1)
                .fetchOne();
    }

    public Long getNotReadCoverNotice(Long staffId){
        return jpaQueryFactory.
                select(staffNotice.count())
                .from(staffNotice)
                .where(staffNotice.receiver.receiverId.eq(staffId)
                        .and(staffNotice.isRead.eq(false)))
                .fetchOne();
    }

    public List<CoverNoticeBoxResDto> getStaffNoticeSpec(Long staffId){
        return jpaQueryFactory
                .select(Projections.constructor(CoverNoticeBoxResDto.class,
                        staffNotice.isRead,
                        staffNotice.id,
                        staffNotice.sender.senderId,
                        staffNotice.sender.senderName,
                        staffNotice.noticeType,
                        staffNotice.createDt,
                        staffNotice.detailsUrl))
                .from(staffNotice)
                .where(staffNotice.receiver.receiverId.eq(staffId)
                        .and(staffNotice.isDeleted.eq(false)))
                .orderBy(staffNotice.createDt.desc())
                .fetch();
    }
}
