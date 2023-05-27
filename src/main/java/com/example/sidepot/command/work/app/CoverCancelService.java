package com.example.sidepot.command.work.app;

import com.example.sidepot.command.notification.work.domain.Sender;
import com.example.sidepot.command.work.domain.CoverManager;
import com.example.sidepot.global.event.Events;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.command.notification.work.domain.CoverManagerId;
import com.example.sidepot.command.notification.work.domain.RejectMessage;
import com.example.sidepot.command.work.repository.CoverManagerRepository;
import com.example.sidepot.command.work.event.AcceptedCoverCanceledEvent;
import com.example.sidepot.command.work.event.RequestedCoverCanceledEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CoverCancelService {

    private final CoverManagerRepository coverManagerRepository;

    /**
     * 대타 요청 자체를 취소한다 -> 삭제처리
     */
    @Transactional
    public void cancelRequestedCover(LoginMember loginMember, Long coverManagerId) {
        CoverManager coverManagerPs = coverManagerRepository.findById(coverManagerId).orElseThrow();
        coverManagerPs.delete(); //삭제 처리
        Events.raise(new RequestedCoverCanceledEvent(
                        new CoverManagerId(coverManagerPs.getId()))); //직원 노티에서 삭제처리
    }

    /**
     * 대타 수락을 취소한다 -> 정책없음
     */
    @Transactional
    public void cancelAcceptedCover(LoginMember loginMember, Long coverNoticeId, RejectMessage rejectMessage) {
        Optional<CoverManager> coverNoticeOp = coverManagerRepository.findById(coverNoticeId);
        CoverManager coverManagerPs = coverNoticeOp.orElseThrow();

        coverManagerPs.isMyNotice(loginMember.getMemberId());
        coverManagerPs.cancel(); //취소 처리
        //
        Events.raise(new AcceptedCoverCanceledEvent(   // 취소 후 알림에 대한 정책이 없음, 개발자가 임시적용
                new CoverManagerId(coverManagerPs.getId()),
                new Sender(coverManagerPs.getAcceptedStaff().getId(), coverManagerPs.getAcceptedStaff().getName()),
                rejectMessage));
        }
}
