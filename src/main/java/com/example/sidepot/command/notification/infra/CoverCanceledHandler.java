package com.example.sidepot.command.notification.infra;


import com.example.sidepot.command.member.domain.StaffRepository;
import com.example.sidepot.command.notification.domain.*;
import com.example.sidepot.command.notification.app.FirebaseMessageService;
import com.example.sidepot.command.store.domain.StoreRepository;
import com.example.sidepot.command.work.domain.CoverManager;
import com.example.sidepot.command.work.event.AcceptorCanceledEvent;
import com.example.sidepot.command.work.event.RequesterCanceledEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CoverCanceledHandler {
    private final FirebaseMessageService firebaseMessageService;
    private final StoreRepository storeRepository;
    private final StaffRepository staffRepository;
    private final OwnerNotificationRepository ownerNotificationRepository;
    private final StaffNotificationRepository staffNotificationRepository;
    @EventListener(value = AcceptorCanceledEvent.class)
    public void cancelByAcceptor(AcceptorCanceledEvent event){
        CoverManager coverManager = event.getCoverManager();
        SNotification sNotification = new SNotification(coverManager.getStoreInfo(), NoticeType.A_STAFF_COVER_CANCEL);
        sNotification.addStaffNotificationBox(new StaffNotificationBox(event.getReceiver()));

        //푸시 알림
        //String token = event.getReceiver().getReceiverToken(); //DB 에서 검색
        //firebaseMessageService.sendMessageTo();

        staffNotificationRepository.save(sNotification);
    }

    @EventListener(value = RequesterCanceledEvent.class)
    public void cancelByRequester(RequesterCanceledEvent event){
        CoverManager coverManager = event.getCoverManager();
        SNotification sNotification = new SNotification(coverManager.getStoreInfo(), NoticeType.R_STAFF_COVER_CANCEL);
        sNotification.addStaffNotificationBox(new StaffNotificationBox(event.getReceiver()));

        //푸시알림
        //코드

        staffNotificationRepository.save(sNotification);
    }

    // 사장은 여기서 처리 하는게 아님
//    @EventListener(value = CoverCanceledEvent.class)
//    public void 사장한테(CoverCanceledEvent event){
//        StoreInfo storeInfo = event.getCoverManager().getStoreInfo();;
//        Store storePs = storeRepository.findById(storeInfo.getStoreId()).orElseThrow();
//
//        Owner ownerPs = storePs.getOwner();
//        ONotification oNotification = new ONotification(storeInfo, NoticeType.OWNER_WORK_CHANGE);
//        oNotification.addOwnerNotificationBox(new OwnerNotificationBox(new Receiver(ownerPs)));
//
//        //푸시 알림
//        ownerNotificationRepository.save(oNotification);
//    }

}
