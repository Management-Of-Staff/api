package com.example.sidepot.command.notification.work.app;

import com.example.sidepot.command.notification.work.domain.StaffCoverNoticeBox;
import com.example.sidepot.command.notification.work.repository.StaffNoticeRepository;
import com.example.sidepot.global.security.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CoverNoticeStatusService {

    private final StaffNoticeRepository staffNoticeRepository;

    /**
     *  알림 읽음 처리
     */
    @Transactional
    public void checkNotice(LoginMember loginMember, Long staffNoticeId){
        StaffCoverNoticeBox staffCoverNoticeBoxPs = staffNoticeRepository.findById(staffNoticeId).orElseThrow();
        staffCoverNoticeBoxPs.checkNotice();
    }

    /**
     *  알림 숨김 처리
     */
    @Transactional
    public void hideNotice(Long staffNoticeId){
        StaffCoverNoticeBox staffCoverNoticeBoxPs = staffNoticeRepository.findById(staffNoticeId).orElseThrow();
        staffCoverNoticeBoxPs.delete();
    }


    /**
     *  화면에 보이는 알림 일괄 삭제(숨김) 처리
     */
    @Transactional
    public void hideAllNotice(List<Long> staffNoticeIds){
        staffNoticeRepository.findAllById(staffNoticeIds)
                .stream()
                .forEach(st -> st.delete());
    }


}
