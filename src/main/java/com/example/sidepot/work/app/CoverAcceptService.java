package com.example.sidepot.work.app;

import com.example.sidepot.global.event.Events;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.member.domain.Staff;
import com.example.sidepot.member.domain.StaffRepository;
import com.example.sidepot.notification.work.domain.CoverManagerId;
import com.example.sidepot.notification.work.domain.NoticeType;
import com.example.sidepot.notification.work.domain.ReceiverId;
import com.example.sidepot.work.domain.AcceptedStaffId;
import com.example.sidepot.work.domain.CoverManager;
import com.example.sidepot.work.domain.RequestedStaffId;
import com.example.sidepot.work.event.CoverWorkAcceptedEvent;
import com.example.sidepot.work.repository.CoverManagerRepository;
import com.example.sidepot.work.domain.SenderId;
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
        Optional<CoverManager> coverManagerOp = coverManagerRepository.findById(coverManagerId);
        CoverManager coverManagerPs = coverManagerOp.orElseThrow();

        //workPossibleCheckService.coverWorkabilityScheduleCheck();

        Optional<Staff> staffOp = staffRepository.findById(loginMember.getMemberId());
        Staff acceptedStaff = staffOp.orElseThrow();

        RequestedStaffId receiver = coverManagerPs.getCoverWorkList().get(0).getRequestedStaff();
        coverManagerPs.accepted(new AcceptedStaffId(acceptedStaff.getMemberId(), acceptedStaff.getMemberName()));
        CoverManager coverManager = new CoverManager(
                NoticeType.ACCEPTED.getMessage(),
                coverManagerPs.getStoreId(),
                new SenderId(acceptedStaff.getMemberId(), acceptedStaff.getMemberName()));

        CoverManager coverManagerPS = coverManagerRepository.save(coverManager);
        Events.raise(new CoverWorkAcceptedEvent(
                new CoverManagerId(coverManagerPS.getId(), coverManagerPS.getSenderId()),
                new ReceiverId(receiver.getRequestedStaffId(), receiver.getUuid())));

    }
}
