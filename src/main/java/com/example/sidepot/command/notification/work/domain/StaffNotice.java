package com.example.sidepot.command.notification.work.domain;

import com.example.sidepot.command.employment.domain.Employment;
import com.example.sidepot.command.work.domain.CoverManager;
import com.example.sidepot.global.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "staff_notice")
@Entity
public class StaffNotice extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private CoverManagerId coverManagerId;
    @Embedded
    private Sender sender;
    @Embedded
    private Receiver receiver;

    @Column(name = "is_read") // 읽음 표시
    private Boolean isRead;

    @Column(name = "is_deleted") // 삭제 표시
    private Boolean isDeleted;

    @Enumerated(EnumType.STRING)
    @Column(name = "notice_type")
    private NoticeType noticeType;

    @Column(name = "is_rejected") //알림에서 거절
    private Boolean isRejected;

    @Enumerated(EnumType.STRING)  //알림에서 거절
    @Column(name = "reject_message")
    private RejectMessage rejectMessage;

    @Column(name = "details_url")
    private String detailsUrl;

    public StaffNotice(CoverManagerId coverManagerId, Sender sender, Receiver receiver, NoticeType noticeType) {
        this.coverManagerId = coverManagerId;
        this.sender = sender;
        this.receiver = receiver;
        this.isRead = false;
        this.isRejected = false;
        this.isDeleted = false;
        this.noticeType = noticeType;
        this.detailsUrl = "/rest/v1/cover-works/notice-box/" + coverManagerId.getCoverManagerId();  //DNS 풀네임 넣어야됨
    }

    public static StaffNotice newStaffNotice(CoverManager coverManager, Employment employment, NoticeType noticeType){
        return new StaffNotice(
                new CoverManagerId(coverManager.getId()),
                new Sender(coverManager.getRequestedStaff().getId(), coverManager.getRequestedStaff().getName()),
                new Receiver(employment.getStaff().getMemberId(), employment.getStaff().getUuid()),
                noticeType);
    }

    private void setDetailsUrl(){
    }

    public void rejected(RejectMessage rejectMessage){
        this.isRejected = true;
        this.rejectMessage = rejectMessage;
    }

    public void delete(){
        this.isDeleted = true;
    }

    public void checkNotice(){
        this.isRead = true;
    }
}
