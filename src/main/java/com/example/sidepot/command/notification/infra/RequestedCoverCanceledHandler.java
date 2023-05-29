package com.example.sidepot.command.notification.infra;

import com.example.sidepot.command.work.domain.StaffCoverNoticeBox;
import com.example.sidepot.command.work.presentation.StaffCoverNoticeRepository;
import com.example.sidepot.command.work.event.AcceptedCoverCanceledEvent;
import com.example.sidepot.command.work.event.RequestedCoverCanceledEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RequestedCoverCanceledHandler {
    private final StaffCoverNoticeRepository staffCoverNoticeRepository;

    @TransactionalEventListener(
            value = RequestedCoverCanceledEvent.class,
            phase = TransactionPhase.BEFORE_COMMIT)
    public void cancelRequestedCover(RequestedCoverCanceledEvent event){
        List<StaffCoverNoticeBox> staffCoverNoticeBoxPsList = staffCoverNoticeRepository.findAllByCoverManagerId(event.getCoverManagerId());
        staffCoverNoticeBoxPsList.stream().forEach(sn -> sn.getIsDeleted());
    }

    @TransactionalEventListener(
            value = RequestedCoverCanceledEvent.class,
            phase = TransactionPhase.BEFORE_COMMIT)
    public void cancelAcceptedCover(AcceptedCoverCanceledEvent event){
        StaffCoverNoticeBox staffCoverNoticeBoxPs = staffCoverNoticeRepository.findByCoverManagerId(event.getCoverManagerId()).orElseThrow();
        staffCoverNoticeBoxPs.rejected(event.getRejectMessage());
    }
}
