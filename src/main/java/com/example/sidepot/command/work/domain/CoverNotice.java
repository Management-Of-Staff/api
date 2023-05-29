package com.example.sidepot.command.work.domain;

import com.example.sidepot.command.notification.domain.NoticeType;
import com.example.sidepot.global.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cover_notice")
@Entity
public class CoverNotice extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_manager_id")
    private CoverManager coverManager;

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

    public CoverNotice(Sender sender, Receiver receiver, NoticeType noticeType) {
        this.sender = sender;
        this.receiver = receiver;
        this.isRead = false;
        this.isRejected = false;
        this.isDeleted = false;
        this.noticeType = noticeType;

    }

    public static CoverNotice newCoverNotice(Sender sender, Receiver receiver, NoticeType noticeType){
        return new CoverNotice(sender, receiver, noticeType);
    }

    public void setCoverManager(CoverManager coverManager){
        this.coverManager = coverManager;
        this.detailsUrl = "/rest/v1/cover-works/notice-box/" + coverManager.getId();
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
