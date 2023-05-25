package com.example.sidepot.command.work.app;

import com.example.sidepot.command.member.domain.Staff;
import com.example.sidepot.command.member.domain.StaffRepository;
import com.example.sidepot.command.notification.work.domain.CoverManagerId;
import com.example.sidepot.command.notification.work.domain.Receiver;
import com.example.sidepot.command.notification.work.domain.Sender;
import com.example.sidepot.command.work.domain.AcceptedStaff;
import com.example.sidepot.command.work.domain.CoverManager;
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
    private final WorkPossibleCheckService workPossibleCheckService;

    @Transactional
    public void acceptCoverWork(LoginMember loginMember, Long coverManagerId){
        CoverManager coverManagerPs = findCoverManager(coverManagerId);

        //workPossibleCheckService.coverWorkabilityScheduleCheck();

        Staff acceptedStaff = findStaff(loginMember.getMemberId());
        //대타 수락 처리
        coverManagerPs.accepted(new AcceptedStaff(acceptedStaff.getMemberId(), acceptedStaff.getMemberName()));

        //알림을 전달받을 대타 요청자정보
        Staff receiver = findStaff(coverManagerPs.getRequestedStaff().getId());

        Events.raise(new CoverWorkAcceptedEvent(
                new CoverManagerId(coverManagerPs.getId()),
                new Sender(acceptedStaff.getMemberId(), acceptedStaff.getMemberName()),
                new Receiver(receiver.getMemberId(), receiver.getMemberName(), receiver.getUuid())));

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
