package com.example.sidepot.store.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "notice_notice_manager")
@NoArgsConstructor
@Entity
public class NoticeNoticeManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_notice_manager_id")
    private Long noticeWriterId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    private Notice notice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_manager_id")
    private NoticeManager noticeManager;
}
