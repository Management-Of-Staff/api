package com.example.sidepot.notification.work.domain;


import com.example.sidepot.member.domain.Staff;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "cover_notice_box")
@Entity
public class CoverNoticeBox {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "notice_notice_box")
    private NoticeNoticeBox noticeNoticeBox;
}
