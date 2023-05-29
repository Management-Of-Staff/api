package com.example.sidepot.command.notification.domain;

import com.example.sidepot.command.work.domain.Receiver;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "owner_notification_box")
@Entity
public class OwnerNotificationBox {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id")
    private ONotification oNotification;
    @Column(name = "is_read")
    private Boolean isRead;
    @Embedded
    private Receiver receiver;

    public OwnerNotificationBox(Receiver receiver) {
        this.isRead = false;
        this.receiver = receiver;
    }

    public void setNotification(ONotification oNotification) {
        this.oNotification = oNotification;
    }
}
