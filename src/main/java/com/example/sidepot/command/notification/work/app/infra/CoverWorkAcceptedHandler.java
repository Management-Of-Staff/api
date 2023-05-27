package com.example.sidepot.command.notification.work.app.infra;

import com.example.sidepot.command.work.event.CoverWorkAcceptedEvent;
import com.example.sidepot.command.notification.firebase.FirebaseMessageService;
import com.example.sidepot.command.notification.work.app.CoverNoticeCreationService;
import com.example.sidepot.command.notification.work.domain.StaffNotice;
import com.example.sidepot.command.notification.work.repository.StaffNoticeRepository;
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
                = coverNoticeCreationService.createAcceptedNotice(event.getCoverNoticeId(), event.getSender(), event.getReceiver());
        staffNoticeRepository.save(acceptedNotice);
        //firebaseMessageService.sendMessageTo();
    }
}
