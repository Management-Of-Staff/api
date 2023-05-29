package com.example.sidepot.command.notification.domain;

import com.example.sidepot.command.work.domain.Receiver;
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
    private SNotification sNotification;
    @Column(name = "is_read")
    private Boolean isRead;
    @Embedded
    private Receiver receiver;

    public StaffNotificationBox(Receiver receiver) {
        this.isRead = false;
        this.receiver = receiver;
    }

    public void setNotification(SNotification sNotification) {
        this.sNotification = sNotification;
    }
}
