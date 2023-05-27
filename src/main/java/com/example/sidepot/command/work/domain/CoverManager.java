package com.example.sidepot.command.work.domain;

import com.example.sidepot.global.domain.BaseEntity;

import com.example.sidepot.command.member.domain.Staff;
import com.example.sidepot.command.store.domain.Store;
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

    public CoverManager(StoreInfo storeInfo, RequestedStaff requestedStaff, List<CoverWork> coverWorkList) {
        this.coverManagerStatus = CoverManagerStatus.WAITING;
        this.storeInfo = storeInfo;
        this.requestedStaff = requestedStaff;
        setCoverWorkList(coverWorkList);
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

    public static CoverManager newCoverManager(Staff requestedStaff, List<CoverWork> coverWorkList, Store storePs){
        return  new CoverManager(
                new StoreInfo(storePs.getStoreId(), storePs.getBranchName(), storePs.getStoreName()),
                new RequestedStaff(requestedStaff.getMemberId(), requestedStaff.getMemberName()),
                coverWorkList);
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
        if(this.coverManagerStatus == CoverManagerStatus.WAITING){
            throw new IllegalStateException("이미 취소된 요청입니다.");
        }
        this.coverManagerStatus = CoverManagerStatus.WAITING;
        this.getCoverWorkList().stream().forEach(cw -> cw.cancel());
    }

    public void delete(){
        this.isDeleted = true;
    }

    public void expire(){
        this.coverManagerStatus = CoverManagerStatus.EXPIRE;
    }

    public void isMyNotice(Long memberId){
        if(!this.acceptedStaff.getId().equals(memberId)){
            throw new IllegalStateException("비정상적 접근");
        }
    }

    public void accepted(AcceptedStaff acceptedStaff) {
        if (this.coverManagerStatus == CoverManagerStatus.ACCEPTED || this.coverManagerStatus == CoverManagerStatus.EXPIRE) {
            throw new IllegalStateException("이미 수락된 대타 요청입니다.");
        }
        this.coverManagerStatus = CoverManagerStatus.ACCEPTED;
        this.coverWorkList.stream().forEach(cw -> cw.acceptCover(acceptedStaff));
    }
}
