package com.example.sidepot.command.notification.domain;

import com.example.sidepot.command.work.domain.CoverManager;
import com.example.sidepot.command.work.domain.CoverNotice;
import com.example.sidepot.command.work.domain.Receiver;
import com.example.sidepot.command.work.domain.StoreInfo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "staff_notification")
public class SNotification extends Notification {

    @OneToMany(mappedBy = "sNotification", cascade = CascadeType.ALL)
    private List<StaffNotificationBox> staffNotificationBoxList = new ArrayList<>();

    public SNotification(StoreInfo storeInfo, NoticeType noticeType) {
        super(storeInfo, noticeType);
    }

    public void setStaffNotificationBoxList(CoverManager coverManager){
        for(CoverNotice coverNotice : coverManager.getCoverNoticeList()){
            StaffNotificationBox staffNotificationBox = new StaffNotificationBox(coverNotice.getReceiver());
            this.staffNotificationBoxList.add(staffNotificationBox);
            staffNotificationBox.setNotification(this);
        }
    }

    public void addStaffNotificationBox(StaffNotificationBox staffNotificationBox) {
        this.staffNotificationBoxList.add(staffNotificationBox);
        staffNotificationBox.setNotification(this);
    }
}
