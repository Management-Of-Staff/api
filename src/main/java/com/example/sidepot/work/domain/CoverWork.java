package com.example.sidepot.work.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cover_work")
@Entity
public class CoverWork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cover_work_id")
    private Long coverWorkId;
    @Embedded
    private RequestedStaffId requestedStaff;
    @Embedded
    private AcceptedStaffId acceptedStaff;
    @Embedded
    private CoverDateTime coverDateTime;
    @Embedded
    private OriginWorkId workTIme;
    @Column(name = "is_accepted")
    private Boolean isAccepted;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cover_manager_id")
    private CoverManager coverManager;

    public CoverWork(CoverDateTime coverDateTime, OriginWorkId originWorkId, RequestedStaffId requestedStaff) {
        this.coverDateTime = coverDateTime;
        this.workTIme = originWorkId;
        this.requestedStaff = requestedStaff;
        this.isAccepted = false;
    }

    public CoverWork setCoverManager(CoverManager coverManager){
        this.coverManager = coverManager;
        return this;
    }

    public void cancel(){
        this.isAccepted = false;
        this.acceptedStaff = null;
    }

    public void acceptCover(AcceptedStaffId acceptedStaff) {
        this.acceptedStaff = acceptedStaff;
        this.isAccepted = true;
    }

    public boolean isOverlapped(CoverDateTime coverDateTime) {
        return  ((this.coverDateTime.getCoverDate().equals(coverDateTime.getCoverDate()))
                && (this.coverDateTime.getStartTime().isBefore(coverDateTime.getEndTime()))
                && (this.coverDateTime.getEndTime().isAfter(coverDateTime.getStartTime())));

    }

    public void isMyRequest(Long memberId){
        if(!(this.getRequestedStaff().getRequestedStaffId().equals(memberId))){
            throw new IllegalStateException("유효하지 않은 접근입니다.");
        }
    }

    public void didMyAccept(Long memberId){
        if(!(this.getAcceptedStaff().getAcceptedStaffId().equals(memberId))){
            throw new IllegalStateException("유효하지 않은 접근입니다.");
        }
    }
}

