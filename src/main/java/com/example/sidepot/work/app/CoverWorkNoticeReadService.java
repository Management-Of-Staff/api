package com.example.sidepot.work.app;

import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.notification.work.domain.CoverNotice;
import com.example.sidepot.notification.work.domain.NoticeMember;
import com.example.sidepot.notification.work.domain.NoticeMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CoverWorkNoticeReadService {

    private final NoticeMemberRepository noticeMemberRepository;
    public List<CoverNotice> readCoverNoticeOfStaff(LoginMember member){
        List<NoticeMember> noticeMemberListPs = noticeMemberRepository.findAllByReceiver_MemberId(member.getMemberId());
        List<CoverNotice> coverNoticeList = noticeMemberListPs.stream().map(o -> o.getCoverNotice()).collect(Collectors.toList());
        return coverNoticeList;
    }

    public void readCoverNoticeThumbNailOfStaff(LoginMember member){

    }
}
