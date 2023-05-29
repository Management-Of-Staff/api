package com.example.sidepot.command.notification.domain;

import com.example.sidepot.command.work.domain.StoreInfo;
import com.example.sidepot.global.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
//@DiscriminatorColumn
//@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public class Notification extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "related_url")
    private String relatedUrl;
    @Embedded
    private StoreInfo storeInfo;
    @Enumerated(EnumType.STRING)
    private NoticeType noticeType;

    public Notification(StoreInfo storeInfo, NoticeType noticeType) {
        this.relatedUrl = noticeType.getRelatedUrl();
        this.storeInfo = storeInfo;
        this.noticeType = noticeType;
    }
}
