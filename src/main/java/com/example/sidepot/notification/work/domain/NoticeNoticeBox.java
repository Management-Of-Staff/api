package com.example.sidepot.notification.work.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "notice_notice_box")
@Entity
public class NoticeNoticeBox {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cover_notice")
    private CoverNotice coverNotice;

    @Column(name = "is_read")
    private Boolean isRead;

}
