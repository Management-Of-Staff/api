package com.example.sidepot.command.notification.infra;

import com.example.sidepot.command.notification.domain.NoticeType;
import com.example.sidepot.command.notification.domain.SNotification;
import com.example.sidepot.command.notification.domain.StaffNotificationBox;
import com.example.sidepot.command.work.domain.CoverManager;
import com.example.sidepot.command.work.domain.Receiver;
import com.example.sidepot.command.work.event.CoverNoticeAllRejectedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NoticeAllRejectedHandler {

    @EventListener(value = CoverNoticeAllRejectedEvent.class)
    public void 알바한테(CoverNoticeAllRejectedEvent event){
        CoverManager coverManager = event.getCoverManager();
        Receiver receiver = new Receiver(coverManager.getRequestedStaff());
        SNotification sNotification = new SNotification(coverManager.getStoreInfo(), NoticeType.ALL_REJECTED);
        sNotification.addStaffNotificationBox(new StaffNotificationBox(receiver));

        //푸시 알림
    }
}
