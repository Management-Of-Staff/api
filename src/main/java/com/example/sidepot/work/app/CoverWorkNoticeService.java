package com.example.sidepot.work.app;

import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.work.dao.CWNotificationReadQuery;
import com.example.sidepot.work.dao.CoverWorkNoticeResDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CoverWorkNoticeService {
    private final CWNotificationReadQuery cwNotificationReadQuery;
    public List<CoverNoticeResDto> readCoverWorkNotice(LoginMember member){
        /* 알림 정책이 있으면 관련 추가 */
        /* 없으면 해당 서비스 삭제 */
       return cwNotificationReadQuery.readCoverWorkNotice(member.getMemberId());
    }
}
