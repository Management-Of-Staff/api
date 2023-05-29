package com.example.sidepot.command.work.app;

import com.example.sidepot.command.member.domain.Staff;
import com.example.sidepot.command.member.domain.StaffRepository;
import com.example.sidepot.command.work.domain.Receiver;
import com.example.sidepot.command.work.domain.CoverManager;
import com.example.sidepot.command.work.event.CoverNoticeAllRejectedEvent;
import com.example.sidepot.global.event.Events;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.command.work.domain.RejectMessage;
import com.example.sidepot.command.work.repository.CoverManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CoverCancelService {

    private final CoverManagerRepository coverManagerRepository;
    private final StaffRepository staffRepository;

    /**
     *  ----------------- 수락되지 않은 요청을 취소하는 기획이 안 보임
     * 대타 요청자가 대타 요청 자체를 취소한다 -> 삭제처리
     *
     */
    @Transactional
    public void cancelCoverByRequestedStaff(LoginMember member, Long coverManagerId) {
        CoverManager coverManagerPs = coverManagerRepository.findById(coverManagerId).orElseThrow();
        coverManagerPs.isRequestedByMe(member.getMemberId());
        coverManagerPs.delete(); //취소 처리 // 및 알림 이벤트 발행
    }

    /**
     * 대타 수락을 취소한다 -> 정책없음
     * 다시 대타 알림이 유효하기 바뀌는지 아예 삭제되는지 등등등
     */
    @Transactional
    public void cancelAcceptedCover(LoginMember member, Long coverNoticeId, RejectMessage rejectMessage) {
        Optional<CoverManager> coverNoticeOp = coverManagerRepository.findById(coverNoticeId);
        CoverManager coverManagerPs = coverNoticeOp.orElseThrow();

        coverManagerPs.isAcceptedByMe(member.getMemberId());
        coverManagerPs.cancel(rejectMessage); //취소 처리 // 및 알림 이벤트 발행
    }

    /**
     * 알림함에서 개인 알림으로 거절했을 때,
     */
    @Transactional
    public void rejectNotice(LoginMember member, Long coverNoticeId, Long coverManagerId, RejectMessage rejectMessage) {
        CoverManager coverManagerPs = coverManagerRepository.findById(coverNoticeId).orElseThrow();// # error
        coverManagerPs.rejectNotice(coverNoticeId, rejectMessage);

        //모두 거절 되었는가?
        coverManagerPs.createAllRejectedNoticeIfPresent();
    }

    private Staff findStaffById(CoverManager coverManagerPs) {
        return staffRepository.findById(coverManagerPs.getRequestedStaff().getId())
                .orElseThrow(() -> new IllegalStateException("요청자가 없네요???"));
    }
}
