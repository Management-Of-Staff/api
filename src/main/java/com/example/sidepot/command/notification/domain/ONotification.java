package com.example.sidepot.command.notification.domain;

import com.example.sidepot.command.work.domain.Receiver;
import com.example.sidepot.command.work.domain.StoreInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "owner_notification")
public class ONotification extends Notification {

    @OneToMany(mappedBy = "oNotification", cascade = CascadeType.ALL)
    private List<OwnerNotificationBox> ownerNotificationBoxList = new ArrayList<>();

    public ONotification(StoreInfo storeInfo, NoticeType noticeType) {
        super(storeInfo, noticeType);
    }

    public void addOwnerNotificationBox(OwnerNotificationBox ownerNotificationBox){
        this.ownerNotificationBoxList.add(ownerNotificationBox);
        ownerNotificationBox.setNotification(this);
    }
    public void addOwnerNotificationBox(Receiver receiver){
        OwnerNotificationBox ownerNotificationBox = new OwnerNotificationBox(receiver);
        this.ownerNotificationBoxList.add(ownerNotificationBox);
        ownerNotificationBox.setNotification(this);
    }
}
