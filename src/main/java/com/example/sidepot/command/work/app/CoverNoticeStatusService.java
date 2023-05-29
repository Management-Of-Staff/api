package com.example.sidepot.command.work.app;

import com.example.sidepot.command.work.domain.RejectMessage;
import com.example.sidepot.command.work.domain.StaffCoverNoticeBox;
import com.example.sidepot.command.work.presentation.StaffCoverNoticeRepository;
import com.example.sidepot.global.security.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CoverNoticeStatusService {

    private final StaffCoverNoticeRepository staffCoverNoticeRepository;

    /**
     *  알림 읽음 처리
     */
    @Transactional
    public void checkNotice(LoginMember loginMember, Long staffNoticeId){
        StaffCoverNoticeBox staffCoverNoticeBoxPs = staffCoverNoticeRepository.findById(staffNoticeId).orElseThrow();
        staffCoverNoticeBoxPs.checkNotice();
    }

    /**
     *  알림 숨김 처리
     */
    @Transactional
    public void hideNotice(Long staffNoticeId){
        StaffCoverNoticeBox staffCoverNoticeBoxPs = staffCoverNoticeRepository.findById(staffNoticeId).orElseThrow();
        staffCoverNoticeBoxPs.delete();
    }


    /**
     *  화면에 보이는 알림 일괄 삭제(숨김) 처리
     */
    @Transactional
    public void hideAllNotice(List<Long> staffNoticeIds){
        staffCoverNoticeRepository.findAllById(staffNoticeIds)
                .stream()
                .forEach(st -> st.delete());
    }

    /**
     *  대타 알림함에서 거절
     */
    @Transactional
    public void rejectNotice(LoginMember member, Long staffCoverNoticeId, RejectMessage rejectMessage){
        StaffCoverNoticeBox coverNotiBoxPs = staffCoverNoticeRepository.findById(staffCoverNoticeId).orElseThrow();// #error
        coverNotiBoxPs.rejected(rejectMessage);
    }
}
