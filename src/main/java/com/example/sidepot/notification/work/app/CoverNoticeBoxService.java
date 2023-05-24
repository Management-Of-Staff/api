package com.example.sidepot.notification.work.app;

import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.notification.work.domain.StaffNotice;
import com.example.sidepot.notification.work.dto.CoverNoticeResDto.*;
import com.example.sidepot.notification.work.repository.StaffNoticeSpecRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class CoverNoticeBoxService {

    private final StaffNoticeSpecRepository staffNoticeSpecRepository;
    @Transactional(readOnly = true)
    public CoverNoticeThumbnailResDto readCoverNoticeThumbnail(LoginMember member){
        Long notReadCoverNotice = staffNoticeSpecRepository.getNotReadCoverNotice(member.getMemberId());
        StaffNotice oneStaffNotice = staffNoticeSpecRepository.getOneStaffNotice(member.getMemberId());

        return new CoverNoticeThumbnailResDto(notReadCoverNotice, oneStaffNotice);
    }

    @Transactional(readOnly = true)
    public List<CoverNoticeBoxResDto> readCoverNoticeSpec(LoginMember member){
        //정책 없음 그냥 싹다 반환
        List<CoverNoticeBoxResDto> staffNoticeList = staffNoticeSpecRepository.getStaffNoticeSpec(member.getMemberId());
        return staffNoticeList;
    }
}
