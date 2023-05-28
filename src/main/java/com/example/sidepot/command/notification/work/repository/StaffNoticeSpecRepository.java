package com.example.sidepot.command.notification.work.repository;

import com.example.sidepot.command.notification.work.domain.StaffCoverNoticeBox;
import com.example.sidepot.command.notification.work.dto.CoverNoticeResDto.CoverNoticeBoxResDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.sidepot.command.notification.work.domain.QStaffCoverNoticeBox.staffCoverNoticeBox;

@RequiredArgsConstructor
@Repository
public class StaffNoticeSpecRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public StaffCoverNoticeBox getOneStaffNotice(Long staffId){
        return jpaQueryFactory
                .selectFrom(staffCoverNoticeBox)
                .where(staffCoverNoticeBox.receiver.receiverId.eq(staffId)
                        .and(staffCoverNoticeBox.isDeleted.eq(false))
                        .and(staffCoverNoticeBox.isRead.eq(false)))
                .orderBy(staffCoverNoticeBox.createDt.desc())
                .limit(1)
                .fetchOne();
    }

    public Long getNotReadCoverNotice(Long staffId){
        return jpaQueryFactory.
                select(staffCoverNoticeBox.count())
                .from(staffCoverNoticeBox)
                .where(staffCoverNoticeBox.receiver.receiverId.eq(staffId)
                        .and(staffCoverNoticeBox.isRead.eq(false)))
                .fetchOne();
    }

    public List<CoverNoticeBoxResDto> getStaffNoticeSpec(Long staffId){
        return jpaQueryFactory
                .select(Projections.constructor(CoverNoticeBoxResDto.class,
                        staffCoverNoticeBox.isRead,
                        staffCoverNoticeBox.id,
                        staffCoverNoticeBox.sender.senderId,
                        staffCoverNoticeBox.sender.senderName,
                        staffCoverNoticeBox.noticeType,
                        staffCoverNoticeBox.createDt,
                        staffCoverNoticeBox.detailsUrl))
                .from(staffCoverNoticeBox)
                .where(staffCoverNoticeBox.receiver.receiverId.eq(staffId)
                        .and(staffCoverNoticeBox.isDeleted.eq(false)))
                .orderBy(staffCoverNoticeBox.createDt.desc())
                .fetch();
    }
}
