package com.example.sidepot.notification.work.app.infra;

import com.example.sidepot.notification.work.domain.StaffNotice;
import com.example.sidepot.notification.work.repository.StaffNoticeRepository;
import com.example.sidepot.work.event.AcceptedCoverCanceledEvent;
import com.example.sidepot.work.event.RequestedCoverCanceledEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RequestedCoverCanceledHandler {
    private final StaffNoticeRepository staffNoticeRepository;

    @TransactionalEventListener(
            value = RequestedCoverCanceledEvent.class,
            phase = TransactionPhase.BEFORE_COMMIT)
    public void cancelRequestedCover(RequestedCoverCanceledEvent event){
        List<StaffNotice> staffNoticePsList = staffNoticeRepository.findAllByCoverManagerId(event.getCoverManagerId());
        staffNoticePsList.stream().forEach(sn -> sn.getIsDeleted());
    }

    @TransactionalEventListener(
            value = RequestedCoverCanceledEvent.class,
            phase = TransactionPhase.BEFORE_COMMIT)
    public void cancelAcceptedCover(AcceptedCoverCanceledEvent event){
        StaffNotice staffNoticePs = staffNoticeRepository.findByCoverManagerId(event.getCoverManagerId()).orElseThrow();
        staffNoticePs.rejected(event.getRejectMessage());
    }
}
