package com.example.sidepot.command.notification.infra;

import com.example.sidepot.command.notification.domain.NoticeType;
import com.example.sidepot.command.notification.domain.Notification;
import com.example.sidepot.command.notification.domain.NotificationRepository;
import com.example.sidepot.command.notification.domain.SNotification;
import com.example.sidepot.command.notification.app.FirebaseMessageService;

import com.example.sidepot.command.work.domain.CoverManager;

import com.example.sidepot.command.work.domain.StoreInfo;
import com.example.sidepot.command.work.event.CoverWorkRequestedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class CoverWorkRequestedHandler {
    private final FirebaseMessageService firebaseMessageService;
    private final NotificationRepository notificationRepository;

    @TransactionalEventListener(
            value = CoverWorkRequestedEvent.class,
            phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW) // 비동기화
    public void NotificationCreateHandler(CoverWorkRequestedEvent event) {
        // 공통 알림 생성
        List<Notification> notifications = crateNotifications(event);

        // 푸시 발송
        //firebaseMessageService.sendMessageTo();

        //공통알림 저장
        notificationRepository.saveAll(notifications);
    }

    private List<Notification> crateNotifications(CoverWorkRequestedEvent event) {
        List<Notification> notifications = new ArrayList<>();
        for(CoverManager coverManager : event.getCoverManagerList()){
            StoreInfo storeInfo = coverManager.getStoreInfo();
            SNotification staffNotification = new SNotification(storeInfo, NoticeType.STAFF_COVER_REQUESTED);
            staffNotification.setStaffNotificationBoxList(coverManager);
            notifications.add(staffNotification);
        }
        return notifications;
    }
}
