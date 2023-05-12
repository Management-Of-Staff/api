package com.example.sidepot.notification.work.domain;

import com.example.sidepot.global.domain.BaseEntity;
import com.example.sidepot.member.domain.Staff;
import com.example.sidepot.store.domain.Store;
import com.example.sidepot.work.domain.CoverWork;
import com.example.sidepot.work.domain.CoverWorkRequest;
import com.example.sidepot.work.domain.WorkTime;
import com.example.sidepot.work.dto.CoverWorkRequestDto.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cover_notice")
@Entity
public class CoverNotice extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message")
    private String message;


    @Column(name = "details_url")//사용 불가
    private String detailsUrl;

    @Column(name = "is_accepted")
    private boolean isAccepted;

    @ManyToOne // 영속성 미결
    private Store store;

    @ManyToOne// 영속성 미결
    private Staff sender;

    @ElementCollection
    private List<LocalDate> coverDateList;

    @OneToMany(mappedBy = "coverNotice", cascade = CascadeType.PERSIST)
    private List<CoverWorkRequest> coverWorkRequestList = new ArrayList<>();

    @OneToMany(mappedBy = "coverNotice", cascade = CascadeType.PERSIST)
    private List<NoticeMember> noticeMemberList = new ArrayList<>();

    @OneToMany//(mappedBy = "coverNotice")
    private List<CoverWork> coverWorkList = new ArrayList<>();

    @OneToMany(mappedBy = "coverNotice")
    private List<NoticeNoticeBox> noticeNoticeBoxList = new ArrayList<>();

    @Builder //테스트 빌더
    public CoverNotice(Long id, String message, String detailsUrl, boolean isAccepted, Store store, Staff sender,
                       List<NoticeMember> noticeMemberList, List<CoverWork> coverWorkList) {
        this.id = id;
        this.message = message;
        this.detailsUrl = detailsUrl;
        this.isAccepted = isAccepted;
        this.store = store;
        this.sender = sender;// this.workTimeList = workTimeList;
        this.noticeMemberList = noticeMemberList;
        this.coverWorkList = coverWorkList;

    }

    public CoverNotice(List<CreateCoverWorkReqDto> createCoverWorkReqDtoList, List<WorkTime> workTimeList){
        //this.workTimeList = workTimeList;
        setCoverWorkRequest(workTimeList);
        this.sender = workTimeList.get(0).getStaff();
        this.store = workTimeList.get(0).getStore();
        this.coverDateList = createCoverWorkReqDtoList.stream().map(dto -> dto.getCoverDate()).collect(Collectors.toList());
        this.isAccepted = false;
        this.message = NoticeMessage.REQUESTED.getMessage();
    }

    public void setCoverWorkRequest(List<WorkTime> workTimeList){
        this.coverWorkRequestList.addAll(workTimeList.stream().map(wt -> new CoverWorkRequest(wt, this)).collect(Collectors.toList()));
    }

    public void setNoticeMember(List<NoticeMember> noticeMemberList){
        noticeMemberList.forEach(o -> {
            this.noticeMemberList.add(o);
            o.setCOverNotice(this);
        });
    }
}
