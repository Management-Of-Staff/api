package com.example.sidepot.command.notification.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "notification")
@Entity

public class Notification {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "related_url")
    private String relatedUrl;
    @Column(name = "sub_title")
    private String subTitle;
    @Enumerated(EnumType.STRING)
    private NoticeType noticeType;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL)
    private List<StaffNotificationBox> staffNotificationBoxList = new ArrayList<>();

    public Notification(String subTitle, NoticeType noticeType) {
        this.relatedUrl = noticeType.getRelatedUrl();
        this.subTitle = subTitle;
        this.noticeType = noticeType;
    }

    public void setStaffNotificationBoxList(StaffNotificationBox staffNotificationBoxList){
        this.staffNotificationBoxList.add(staffNotificationBoxList);
    }
}
