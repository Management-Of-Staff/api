package com.example.sidepot.work.domain;

import com.example.sidepot.global.domain.BaseEntity;
import com.example.sidepot.work.dto.CoverWorkRequestDto.CreateCoverWorkReqDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "cover_work_notification")
@Entity
public class CoverWorkNotice extends BaseEntity {
    @Id @Column(name = "cover_work_notice_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coverWorkNoticeId;

    @Column(name = "store_id")
    private Long storeId;

    @Column(name = "staff_id")
    private Long staffId;

    @Column(name = "message")
    private String message;

    @Column(name = "details_url")
    private String detailsUrl;

    @Column(name = "cover_notification_type")
    private CoverNoticeType coverNoticeType;

    @Column(name = "read_status")
    private boolean readStatus;

    @Column(name = "is_accepted")
    private boolean isAccepted;

    @OneToMany(mappedBy = "coverWorkNotice")
    private List<CoverWork> coverWorkList = new ArrayList<>();

    @Builder
    public CoverWorkNotice(Long coverWorkNoticeId, Long storeId, Long staffId, String message,
                           CoverNoticeType coverNoticeType,
                           boolean readStatus, boolean isAccepted) {
        this.coverWorkNoticeId = coverWorkNoticeId;
        this.storeId = storeId;
        this.staffId = staffId;
        this.message = message;
        this.coverNoticeType = coverNoticeType;
        this.readStatus = readStatus;
        this.isAccepted = isAccepted;
    }

    public CoverWorkNotice(CreateCoverWorkReqDto createCoverWorkReqDto){
        this.staffId = createCoverWorkReqDto.getRequestedStaffId();
        this.storeId = createCoverWorkReqDto.getStoreId();
        this.coverNoticeType = CoverNoticeType.REQUESTED;
        this.readStatus = false;
        this.isAccepted = false;
        this.message = null;
    }
}