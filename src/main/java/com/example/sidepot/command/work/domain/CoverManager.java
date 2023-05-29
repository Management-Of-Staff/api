package com.example.sidepot.command.work.domain;

import com.example.sidepot.command.notification.domain.NoticeType;
import com.example.sidepot.command.work.event.AcceptorCanceledEvent;
import com.example.sidepot.command.work.event.CoverNoticeAllRejectedEvent;
import com.example.sidepot.command.work.event.RequesterCanceledEvent;
import com.example.sidepot.global.domain.BaseEntity;

import com.example.sidepot.command.member.domain.Staff;
import com.example.sidepot.command.store.domain.Store;
import com.example.sidepot.global.event.Events;
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
    @Column(name = "is_deleted") // 수락되지 않은 요청을 취소 -> 삭제 처리
    private Boolean isDeleted;
    @Enumerated(EnumType.STRING)
    @Column(name = "cover_notice_status")
    private CoverManagerStatus coverManagerStatus;
    @Embedded
    private StoreInfo storeInfo;
    @Embedded
    private RequestedStaff requestedStaff;
    @Embedded
    private AcceptedStaff acceptedStaff;

    @OneToMany(mappedBy = "coverManager", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CoverWork> coverWorkList = new ArrayList<>();

    @OneToMany(mappedBy = "coverManager", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CoverNotice> coverNoticeList = new ArrayList<>();

    public CoverManager(StoreInfo storeInfo, RequestedStaff requestedStaff, List<CoverWork> coverWorkList) {
        this.coverManagerStatus = CoverManagerStatus.WAITING;
        this.storeInfo = storeInfo;
        this.requestedStaff = requestedStaff;
        //setCoverWorkList(coverWorkList);
        this.isDeleted = false;
    }

    public CoverManager(StoreInfo storeInfo, RequestedStaff requestedStaff) {
        this.coverManagerStatus = CoverManagerStatus.WAITING;
        this.storeInfo = storeInfo;
        this.requestedStaff = requestedStaff;
        this.isDeleted = false;
    }


    //테스트셋
    public CoverManager(StoreInfo storeInfo, RequestedStaff requestedStaff,
                        AcceptedStaff acceptedStaff, List<CoverWork> coverWorkList) {
        this.coverManagerStatus = CoverManagerStatus.ACCEPTED;
        this.storeInfo = storeInfo;
        this.requestedStaff = requestedStaff;
        this.acceptedStaff = acceptedStaff;
        setCoverWorkList(coverWorkList);
        this.isDeleted = false;
    }

    public static CoverManager newCoverManager(Staff requestedStaff, Store storePs) {
        return new CoverManager(
                new StoreInfo(storePs.getStoreId(), storePs.getBranchName(), storePs.getStoreName()),
                new RequestedStaff(requestedStaff.getMemberId(), requestedStaff.getMemberName()));
    }

    public CoverManager addCoverWork(CoverWork coverWork) {
        this.coverWorkList.add(coverWork);
        coverWork.setCoverManager(this);
        return this;
    }

    public CoverManager addCoverNotice(CoverNotice coverNotice) {
        this.coverNoticeList.add(coverNotice);
        coverNotice.setCoverManager(this);
        return this;
    }

    private void setCoverWorkList(List<CoverWork> coverWorkList) {
        this.coverWorkList.addAll(
                coverWorkList.stream()
                        .map(cw -> cw.setCoverManager(this))
                        .collect(Collectors.toList()));
    }

    // 수락 이후 대타를 수락자가 취소
    public void cancel(RejectMessage rejectMessage) {
        //취소 되었을 때 정책이 없음
        //this.coverNoticeStatus = CoverNoticeStatus.WAITING
        this.coverManagerStatus = CoverManagerStatus.CANCEL;
        this.coverWorkList.stream().forEach(cw -> cw.cancel());
        this.coverNoticeList.stream().forEach(cn -> cn.rejected(rejectMessage));
        Events.raise(new AcceptorCanceledEvent(   // 취소 후 알림에 대한 정책이 없음, 개발자가 임시적용
                this, new Sender(requestedStaff), new Receiver(acceptedStaff), rejectMessage));

        //사장 푸시알림말고 스케줄 변동 처리도 해야함
    }

    // 수락 이후 대타를 요청자가 취소
    public void delete(){
        this.isDeleted = true;
        this.coverManagerStatus = CoverManagerStatus.CANCEL;
        this.coverWorkList.stream().forEach(cw -> cw.delete());
        this.coverNoticeList.forEach(cn -> cn.delete());
        Events.raise(new RequesterCanceledEvent(   // 취소 후 알림에 대한 정책이 없음, 개발자가 임시적용
               this, new Sender(acceptedStaff), new Receiver(requestedStaff)));

        //사장 푸시알림말고 스케줄 변동 알림 처리도 해야함
    }

    // 스케줄러
    public void expire(){
        this.coverManagerStatus = CoverManagerStatus.EXPIRE;
    }

    public void isRequestedByMe(Long memberId){
        if(!this.acceptedStaff.getId().equals(memberId)){
            throw new IllegalStateException("비정상적 접근");
        }
    }

    public void isAcceptedByMe(Long memberId){
        if(!this.acceptedStaff.getId().equals(memberId)){
            throw new IllegalStateException("비정상적 접근");
        }
    }

    // 알림으로 수락했을 때 -> 트랙잭션 락 걸어야함
    public void accepted(AcceptedStaff acceptedStaff) {
        if (this.coverManagerStatus == CoverManagerStatus.ACCEPTED || this.coverManagerStatus == CoverManagerStatus.EXPIRE) {
            throw new IllegalStateException("이미 수락된 대타 요청입니다.");
        }
        this.coverManagerStatus = CoverManagerStatus.ACCEPTED;
        this.coverWorkList.stream().forEach(cw -> cw.acceptCover(acceptedStaff));
    }

    public void rejectNotice(Long coverNoticeId, RejectMessage rejectMessage){
        CoverNotice coverNotice = this.coverNoticeList.stream().filter(cn -> cn.getId().equals(coverNoticeId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("알림이 없는 알림이네요")); // # error
        coverNotice.rejected(rejectMessage);
    }

    public void createAllRejectedNoticeIfPresent(){
        if (isAllRejected()){
            CoverNotice coverNotice = new CoverNotice(new Sender(requestedStaff), new Receiver(requestedStaff), NoticeType.ALL_REJECTED);
            this.coverNoticeList.add(coverNotice);
            coverNotice.setCoverManager(this);
            Events.raise(new CoverNoticeAllRejectedEvent(this));
        }
    }
    private boolean isAllRejected(){
        if(this.coverManagerStatus == CoverManagerStatus.ACCEPTED) return false;
        for(CoverNotice coverNotice : this.coverNoticeList){
            if(coverNotice.getIsRejected() == false) return false;
        }
        return true;
    }
}
