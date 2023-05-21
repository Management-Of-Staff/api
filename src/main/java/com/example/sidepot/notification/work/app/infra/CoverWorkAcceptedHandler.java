package com.example.sidepot.notification.work.app.infra;

import com.example.sidepot.notification.firebase.FirebaseMessageService;
import com.example.sidepot.notification.work.app.CoverNoticeCreationService;
import com.example.sidepot.notification.work.domain.StaffNotice;
import com.example.sidepot.notification.work.domain.StaffNoticeRepository;
import com.example.sidepot.work.event.CoverWorkAcceptedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;


@RequiredArgsConstructor
@Service
public class CoverWorkAcceptedHandler {
    private final CoverNoticeCreationService coverNoticeCreationService;
    private final StaffNoticeRepository staffNoticeRepository;

    private final FirebaseMessageService firebaseMessageService;
    @TransactionalEventListener(
            value = CoverWorkAcceptedEvent.class,
            phase = TransactionPhase.BEFORE_COMMIT)
    public void CoverNoticeCreateHandler(CoverWorkAcceptedEvent event){
        StaffNotice acceptedNotice
                = coverNoticeCreationService.createAcceptedNotice(event.getCoverNoticeId(), event.getReceiverId());
        staffNoticeRepository.save(acceptedNotice);
        //firebaseMessageService.sendMessageTo();
    }
}
