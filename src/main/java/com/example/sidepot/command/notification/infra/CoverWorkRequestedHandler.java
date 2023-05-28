package com.example.sidepot.command.notification.infra;

import com.example.sidepot.command.employment.domain.Employment;
import com.example.sidepot.command.employment.repository.EmploymentRepository;
import com.example.sidepot.command.notification.common.NoticeType;
import com.example.sidepot.command.notification.common.Notification;
import com.example.sidepot.command.notification.common.NotificationRepository;
import com.example.sidepot.command.notification.common.StaffNotificationBox;
import com.example.sidepot.command.notification.work.domain.CoverManagerId;
import com.example.sidepot.command.notification.work.domain.Receiver;
import com.example.sidepot.command.notification.work.domain.Sender;
import com.example.sidepot.command.notification.work.repository.StaffNoticeRepository;
import com.example.sidepot.command.notification.firebase.FirebaseMessageService;
import com.example.sidepot.command.notification.work.app.CoverNoticeCreationService;
import com.example.sidepot.command.notification.work.domain.StaffCoverNoticeBox;
import com.example.sidepot.command.work.domain.CoverManager;
import com.example.sidepot.command.work.event.CoverWorkRequestedEvent;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class CoverWorkRequestedHandler {

    private final StaffNoticeRepository staffNoticeRepository;
    private final NotificationRepository notificationRepository;
    private final EmploymentRepository employmentRepository;
    private final FirebaseMessageService firebaseMessageService;

    @TransactionalEventListener(
            value = CoverWorkRequestedEvent.class)
           //phase = TransactionPhase.BEFORE_COMMIT)  // 트랙잭션 합류 필수
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void coverNoticeCreateHandler(CoverWorkRequestedEvent event) throws IOException {
        Map<CoverManager, List<Receiver>> cmMapByStore = getCmMapByStore(event.getCoverManagerList());

        List<StaffCoverNoticeBox> requestedNotice
                = createRequestedNotice(cmMapByStore);

        staffNoticeRepository.saveAll(requestedNotice);
    }

    @TransactionalEventListener(
            value = CoverWorkRequestedEvent.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void coverNotificationCreateHandler(CoverWorkRequestedEvent event){
        Map<CoverManager, List<Receiver>> cmMapByStore = getCmMapByStore(event.getCoverManagerList());

        List<Notification> notifications = createNotifications(cmMapByStore);
        notificationRepository.saveAll(notifications);

        List<Receiver> allReceiver = cmMapByStore.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
        List<String> allTokens = allReceiver.stream().map(rec -> rec.getReceiverToken()).collect(Collectors.toList());
        //firebaseMessageService.sendMessageTo("tokens.get(0)","아오", "시발");
    }


    private  List<Notification> createNotifications(Map<CoverManager, List<Receiver>> cmMapByStore) {
        List<Notification> notifications = new ArrayList<>();
        for(CoverManager coverManager : cmMapByStore.keySet()){
            Notification notification = new Notification(coverManager.getStoreInfo().getStoreName(), NoticeType.COVER_ACCEPTED);
            List<Receiver> receiverList = cmMapByStore.get(coverManager);
            for(Receiver receiver : receiverList){
                notification.setStaffNotificationBoxList(new StaffNotificationBox(notification, receiver));
            }
            notifications.add(notification);
        }
        return notifications;
    }


    private Map<CoverManager, List<Receiver>> getCmMapByStore(List<CoverManager> coverManagerList){
        Map<CoverManager, List<Receiver>> managerListMap = new HashMap<>();
        for (CoverManager coverManager : coverManagerList) {
            List<Receiver> receiverList = new ArrayList<>();
            List<Employment> employmentList = findAllEmploymentByStore(coverManager);
            for (Employment employment : employmentList) {
                if(!(employment.getStaff().getMemberId().equals(coverManager.getRequestedStaff().getId()))) {
                    receiverList.add(new Receiver(employment.getStaff()));
                }
            }
            managerListMap.put(coverManager, receiverList);
        }
        return managerListMap;
    }


    private List<StaffCoverNoticeBox> createRequestedNotice(Map<CoverManager, List<Receiver>> cmListMap) {
        List<StaffCoverNoticeBox> staffCnbList = new ArrayList<>();
        for(CoverManager coverManager : cmListMap.keySet()){
            for(Receiver receiver : cmListMap.get(coverManager)){
                staffCnbList.add(StaffCoverNoticeBox.newStaffNotice(coverManager, receiver, NoticeType.REQUESTED));
            }
        }
        return staffCnbList;
    }

    private List<Employment> findAllEmploymentByStore(CoverManager coverManager) {
        List<Employment> empList = employmentRepository.findAllByStore_StoreId(coverManager.getStoreInfo().getStoreId());
        return empList;
    }
}
