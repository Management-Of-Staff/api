package com.example.sidepot.work.domain;

import com.example.sidepot.global.domain.BaseEntity;
import com.example.sidepot.global.event.Events;

import com.example.sidepot.notification.work.domain.CoverManagerId;
import com.example.sidepot.notification.work.domain.NoticeType;
import com.example.sidepot.notification.work.domain.ReceiverId;
import com.example.sidepot.notification.work.domain.RejectMessage;
import com.example.sidepot.work.event.CoverWorkAcceptedEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cover_manager")
@Entity
public class CoverManager extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String message;

    @Column(name = "is_deleted") //요청 취소
    private Boolean isDeleted;

    @Column(name = "is_rejected") //수락 취소
    private Boolean isRejected;

    @Enumerated(EnumType.STRING) //수락 취소 거절 이유
    @Column(name = "reject_message")
    private RejectMessage rejectMessage;

    @Column(name = "is_all_success") //대타 근무가 실제로 성사 "모두" 완료되었는가?
    private Boolean isAllSuccess;

    @Enumerated(EnumType.STRING)
    @Column(name = "cover_notice_status")
    private CoverManagerStatus coverManagerStatus;

    @Embedded
    private StoreId storeId;

    @Embedded
    private SenderId senderId;

    @OneToMany(mappedBy = "coverManager", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CoverWork> coverWorkList = new ArrayList<>();

    public CoverManager(String message, StoreId store, SenderId sender, List<CoverWork> coverWorkList) {
        this.message = message;
        this.coverManagerStatus = CoverManagerStatus.WAITING;
        //this.noticeType = NoticeType.REQUESTED;
        this.storeId = store;
        this.senderId = sender;
        setCoverWorkList(coverWorkList);
        this.isDeleted = false;
    }

    public CoverManager(String message, StoreId storeId, SenderId senderId) {
        this.message = message;
        this.coverManagerStatus = CoverManagerStatus.ACCEPTED;
        //this.noticeType = NoticeType.ACCEPTED;
        this.storeId = storeId;
        this.senderId = senderId;
        this.isDeleted = false;
    }

    private void setCoverWorkList(List<CoverWork> coverWorkList){
        this.coverWorkList.addAll(
                coverWorkList.stream()
                    .map(cw -> cw.setCoverManager(this))
                    .collect(Collectors.toList()));
    }

    public void cancel(){
        //취소 되었을 때 정책이 없음
        //this.coverNoticeStatus = CoverNoticeStatus.WAITING
        if(this.coverManagerStatus == CoverManagerStatus.CANCEL){
            throw new IllegalStateException("이미 취소된 요청입니다.");
        }
        this.coverManagerStatus = CoverManagerStatus.CANCEL;
        this.getCoverWorkList().stream().forEach(cw -> cw.cancel());
    }

    public void delete(){
        this.isDeleted = true;
    }

    public void expire(){
        this.coverManagerStatus = CoverManagerStatus.EXPIRE;
    }

    public void isMyNotice(Long memberId){
        if(!this.senderId.getSenderId().equals(memberId)){
            throw new IllegalStateException("비정상적 접근");
        }
    }

    public void setIsAllSuccess(){
        this.isAllSuccess = true;
    }

    public void accepted(AcceptedStaffId acceptedStaff) {
        if (this.coverManagerStatus == CoverManagerStatus.ACCEPTED || this.coverManagerStatus == CoverManagerStatus.EXPIRE) {
            throw new IllegalStateException("이미 수락된 대타 요청입니다.");
        }
        this.coverManagerStatus = CoverManagerStatus.ACCEPTED;
        this.coverWorkList.stream().forEach(cw -> cw.acceptCover(acceptedStaff));
    }
}
