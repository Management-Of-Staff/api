package com.example.sidepot.command.notification.infra;

import com.example.sidepot.command.member.domain.Owner;
import com.example.sidepot.command.notification.domain.*;
import com.example.sidepot.command.notification.firebase.FirebaseMessageService;
import com.example.sidepot.command.store.domain.Store;
import com.example.sidepot.command.store.domain.StoreRepository;
import com.example.sidepot.command.work.domain.CoverManager;
import com.example.sidepot.command.work.domain.Receiver;
import com.example.sidepot.command.work.domain.StoreInfo;
import com.example.sidepot.command.work.event.CoverWorkAcceptedEvent;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;


@RequiredArgsConstructor
@Service
public class CoverWorkAcceptedHandler {
    private final StoreRepository storeRepository;
    private final OwnerNotificationRepository ownerNotificationRepository;
    private final StaffNotificationRepository staffNotificationRepository;
    private final FirebaseMessageService firebaseMessageService;

    @TransactionalEventListener(
            value = CoverWorkAcceptedEvent.class,
            phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void CoverNoticeCreateStaffHandler(CoverWorkAcceptedEvent event){
        CoverManager coverManager = event.getCoverManager();

        SNotification sNotification = new SNotification(coverManager.getStoreInfo(), NoticeType.STAFF_COVER_ACCEPTED);
        sNotification.addStaffNotificationBox(new StaffNotificationBox(event.getReceiver()));
        staffNotificationRepository.save(sNotification);
        //firebaseMessageService.sendMessageTo();

    }

    @TransactionalEventListener(
            value = CoverWorkAcceptedEvent.class,
            phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void CoverNoticeCreateOwnerHandler(CoverWorkAcceptedEvent event){
        CoverManager coverManager = event.getCoverManager();

        StoreInfo storeInfo = coverManager.getStoreInfo();
        Store storePs = storeRepository.findById(storeInfo.getStoreId()).orElseThrow();// #error
        Owner ownerPs = storePs.getOwner();
        Receiver owner = new Receiver(ownerPs);
        ONotification oNotification = new ONotification(coverManager.getStoreInfo(), NoticeType.OWNER_WORK_CHANGE);
        oNotification.addOwnerNotificationBox(new OwnerNotificationBox(owner));

        ownerNotificationRepository.save(oNotification);
        //firebaseMessageService.sendMessageTo();
    }
}
