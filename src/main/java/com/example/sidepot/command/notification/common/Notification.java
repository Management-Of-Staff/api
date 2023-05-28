package com.example.sidepot.command.notification.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "notification")
@Entity

public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "related_url")
    private String relatedUrl;

    @Enumerated(EnumType.STRING)
    private NoticeType noticeType;
}
