package com.example.sidepot.command.work.presentation;

import com.example.sidepot.command.work.domain.CoverNotice;
import com.example.sidepot.command.work.dto.CoverNoticeResDto.CoverNoticeBoxResDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.sidepot.command.work.domain.QCoverNotice.coverNotice;

@RequiredArgsConstructor
@Repository
public class StaffCoverNoticeSpecRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public CoverNotice getOneStaffNotice(Long staffId){
        return jpaQueryFactory
                .selectFrom(coverNotice)
                .where(coverNotice.receiver.receiverId.eq(staffId)
                        .and(coverNotice.isDeleted.eq(false))
                        .and(coverNotice.isRead.eq(false)))
                .orderBy(coverNotice.createDt.desc())
                .limit(1)
                .fetchOne();
    }

    public Long getNotReadCoverNotice(Long staffId){
        return jpaQueryFactory.
                select(coverNotice.count())
                .from(coverNotice)
                .where(coverNotice.receiver.receiverId.eq(staffId)
                        .and(coverNotice.isRead.eq(false)))
                .fetchOne();
    }

    public List<CoverNoticeBoxResDto> getStaffNoticeSpec(Long staffId){
        return jpaQueryFactory
                .select(Projections.constructor(CoverNoticeBoxResDto.class,
                        coverNotice.isRead,
                        coverNotice.id,
                        coverNotice.sender.senderId,
                        coverNotice.sender.senderName,
                        coverNotice.noticeType,
                        coverNotice.createDt,
                        coverNotice.detailsUrl))
                .from(coverNotice)
                .where(coverNotice.receiver.receiverId.eq(staffId)
                        .and(coverNotice.isDeleted.eq(false)))
                .orderBy(coverNotice.createDt.desc())
                .fetch();
    }
}
