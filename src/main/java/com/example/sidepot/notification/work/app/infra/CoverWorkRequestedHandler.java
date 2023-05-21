package com.example.sidepot.notification.work.app.infra;

import com.example.sidepot.notification.firebase.FirebaseMessageService;
import com.example.sidepot.notification.work.app.CoverNoticeCreationService;
import com.example.sidepot.notification.work.domain.StaffNotice;
import com.example.sidepot.notification.work.domain.StaffNoticeRepository;
import com.example.sidepot.work.event.CoverWorkRequestedEvent;
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
        List<StaffNotice> requestedNotice
                = coverNoticeCreationService.createRequestedNotice(event.getCoverManagerList());

        List<String> tokens = requestedNotice.stream()
                .map(rn -> rn.getReceiverId().getReceiverToken())
                .collect(Collectors.toList());
        staffNoticeRepository.saveAll(requestedNotice);
        firebaseMessageService.sendMessageTo("tokens.get(0)","아오", "시발");
    }
}
