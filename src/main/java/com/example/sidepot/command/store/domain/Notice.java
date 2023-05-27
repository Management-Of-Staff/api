package com.example.sidepot.command.store.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "notice")
@NoArgsConstructor
@Entity
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long noticeId;

    @Column(name = "notice_title")
    private String noticeTitle;

    @Column(name = "notice_content")
    private String noticeContent;

    @OneToMany(mappedBy = "notice")
    private List<NoticeImage> noticeImageList = new ArrayList<>();

    @OneToMany(mappedBy = "notice")
    private List<NoticeNoticeManager> noticeNoticeManagers = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_writer_id")
    private NoticeWriter noticeWriter;

    @Column(name = "create_time")
    private LocalDateTime createTime;
}
