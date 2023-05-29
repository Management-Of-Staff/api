package com.example.sidepot.command.work.app;

import com.example.sidepot.command.work.domain.CoverNotice;
import com.example.sidepot.command.work.repository.CoverNoticeRepository;
import com.example.sidepot.global.security.LoginMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CoverNoticeStatusService {

    private final CoverNoticeRepository coverNoticeRepository;

    /**
     *  알림 읽음 처리
     */
    @Transactional
    public void checkNotice(LoginMember loginMember, Long staffNoticeId){
        CoverNotice coverNoticePs = coverNoticeRepository.findById(staffNoticeId).orElseThrow();
        coverNoticePs.checkNotice();
    }

    /**
     *  알림 숨김 처리
     */
    @Transactional
    public void hideNotice(Long staffNoticeId){
        CoverNotice coverNoticePs = coverNoticeRepository.findById(staffNoticeId).orElseThrow();
        coverNoticePs.delete();
    }


    /**
     *  화면에 보이는 알림 일괄 삭제(숨김) 처리
     */
    @Transactional
    public void hideAllNotice(List<Long> staffNoticeIds){
        coverNoticeRepository.findAllById(staffNoticeIds)
                .stream()
                .forEach(st -> st.delete());
    }


}
