package com.example.sidepot.command.work.app;

import com.example.sidepot.command.member.domain.Staff;
import com.example.sidepot.command.member.domain.StaffRepository;
import com.example.sidepot.command.notification.domain.NoticeType;
import com.example.sidepot.command.work.domain.*;
import com.example.sidepot.command.work.event.CoverWorkAcceptedEvent;
import com.example.sidepot.command.work.repository.CoverManagerRepository;
import com.example.sidepot.global.event.Events;
import com.example.sidepot.global.security.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CoverAcceptService {

    private final CoverManagerRepository coverManagerRepository;
    private final StaffRepository staffRepository;

    /**
     * 대타 수락
     */
    @Transactional
    public void acceptCoverWork(LoginMember member, Long coverManagerId) {
        Staff acceptedStaff = findStaff(member.getMemberId());

        CoverManager coverManagerPs = findCoverManager(coverManagerId);

        //workPossibleCheckService.coverWorkabilityScheduleCheck(coverManagerPs.getCoverWorkList());

        coverManagerPs.accepted(new AcceptedStaff(acceptedStaff.getMemberId(), acceptedStaff.getMemberName()));

        Staff requestedStaff = findStaff(coverManagerPs.getRequestedStaff().getId());

        Sender sender = new Sender(acceptedStaff);
        Receiver receiver = new Receiver(requestedStaff);
        coverManagerPs.addCoverNotice(new CoverNotice(sender,receiver, NoticeType.STAFF_COVER_ACCEPTED));

        Events.raise(new CoverWorkAcceptedEvent(coverManagerPs, sender, receiver));

        // 사장님 스케쥴 변동 알림함 이벤트 레이즈
        // 사장님 공통 알림 위임 이벤트
    }


    private CoverManager findCoverManager(Long coverManagerId) {
        Optional<CoverManager> coverManagerOp = coverManagerRepository.findById(coverManagerId);
        CoverManager coverManagerPs = coverManagerOp.orElseThrow();
        return coverManagerPs;
    }

    private Staff findStaff(Long staffId) {
        Optional<Staff> staffOp = staffRepository.findById(staffId);
        Staff acceptedStaff = staffOp.orElseThrow();
        return acceptedStaff;
    }
}
