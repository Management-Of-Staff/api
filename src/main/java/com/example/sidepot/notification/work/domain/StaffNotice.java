package com.example.sidepot.notification.work.domain;

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
    @Column(name = "staff_notice_id")
    private Long staffNoticeId;
    @Embedded
    private CoverManagerId coverManagerId;

    @Column(name = "receiver_id")
    private ReceiverId receiverId;

    @Column(name = "is_read") // 읽음 표시
    private Boolean isRead;

    @Column(name = "is_deleted") // 삭제 표시
    private Boolean isDeleted;

    @Column(name = "is_request_canceled") //해당 알림이 수락되었다가 취소 된 것을 표시
    private Boolean isRequestCanceled;

    @Enumerated(EnumType.STRING)
    @Column(name = "notice_type")
    private NoticeType noticeType;

    @Column(name = "is_rejected") //알림에서 거절
    private Boolean isRejected;

    @Enumerated(EnumType.STRING)  //알림에서 거절
    @Column(name = "reject_message")
    private RejectMessage rejectMessage;

    public StaffNotice(CoverManagerId coverManagerId, ReceiverId receiverId, NoticeType noticeType) {
        this.coverManagerId = coverManagerId;
        this.receiverId = receiverId;
        this.isRead = false;
        this.isRejected = false;
        this.isDeleted = false;
        this.noticeType = noticeType;
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
