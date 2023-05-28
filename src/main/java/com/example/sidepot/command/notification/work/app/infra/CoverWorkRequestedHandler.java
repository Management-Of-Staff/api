package com.example.sidepot.command.notification.work.app.infra;

import com.example.sidepot.command.notification.work.repository.StaffNoticeRepository;
import com.example.sidepot.command.notification.firebase.FirebaseMessageService;
import com.example.sidepot.command.notification.work.app.CoverNoticeCreationService;
import com.example.sidepot.command.notification.work.domain.StaffCoverNoticeBox;
import com.example.sidepot.command.work.event.CoverWorkRequestedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class CoverWorkRequestedHandler {
    private final CoverNoticeCreationService coverNoticeCreationService;
    private final StaffNoticeRepository staffNoticeRepository;
    private final FirebaseMessageService firebaseMessageService;

    @TransactionalEventListener(
            value = CoverWorkRequestedEvent.class,
            phase = TransactionPhase.BEFORE_COMMIT)
    public void coverNoticeCreateHandler(CoverWorkRequestedEvent event) throws IOException {
        List<StaffCoverNoticeBox> requestedNotice
                = coverNoticeCreationService.createRequestedNotice(event.getCoverManagerList());

        List<String> tokens = requestedNotice.stream()
                .map(rn -> rn.getReceiver().getReceiverToken())
                .collect(Collectors.toList());
        staffNoticeRepository.saveAll(requestedNotice);
        firebaseMessageService.sendMessageTo("tokens.get(0)","아오", "시발");
    }
}
