package com.example.sidepot.command.notification.infra;

import com.example.sidepot.command.work.event.CoverWorkAcceptedEvent;
import com.example.sidepot.command.notification.firebase.FirebaseMessageService;
import com.example.sidepot.command.work.app.CoverNoticeCreationService;
import com.example.sidepot.command.work.domain.StaffCoverNoticeBox;
import com.example.sidepot.command.work.presentation.StaffCoverNoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;


@RequiredArgsConstructor
@Service
public class CoverWorkAcceptedHandler {
    private final CoverNoticeCreationService coverNoticeCreationService;
    private final StaffCoverNoticeRepository staffCoverNoticeRepository;
    private final FirebaseMessageService firebaseMessageService;
    @TransactionalEventListener(
            value = CoverWorkAcceptedEvent.class,
            phase = TransactionPhase.BEFORE_COMMIT)
    public void CoverNoticeCreateHandler(CoverWorkAcceptedEvent event){
        StaffCoverNoticeBox acceptedNotice
                = coverNoticeCreationService.createAcceptedNotice(event.getCoverNoticeId(), event.getSender(), event.getReceiver());
        staffCoverNoticeRepository.save(acceptedNotice);
        //firebaseMessageService.sendMessageTo();
    }
}
