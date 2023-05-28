package com.example.sidepot.command.notification.common;

import com.example.sidepot.command.notification.work.domain.Receiver;
import com.example.sidepot.global.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "staff_notification_box")
@Entity
public class StaffNotificationBox extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id")
    private Notification notification;
    @Column(name = "is_read")
    private Boolean isRead;
    @Embedded
    private Receiver receiver;

    public StaffNotificationBox(Notification notification, Receiver receiver) {
        this.notification = notification;
        this.isRead = false;
        this.receiver = receiver;
    }
}
