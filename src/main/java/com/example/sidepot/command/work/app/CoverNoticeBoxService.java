package com.example.sidepot.command.work.app;

import com.example.sidepot.command.work.domain.CoverNotice;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.command.work.dto.CoverNoticeResDto.*;
import com.example.sidepot.command.work.presentation.StaffCoverNoticeSpecRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class CoverNoticeBoxService {

    private final StaffCoverNoticeSpecRepository staffCoverNoticeSpecRepository;
    @Transactional(readOnly = true)
    public CoverNoticeThumbnailResDto readCoverNoticeThumbnail(LoginMember member){
        Long notReadCoverNotice = staffCoverNoticeSpecRepository.getNotReadCoverNotice(member.getMemberId());
        CoverNotice oneCoverNotice = staffCoverNoticeSpecRepository.getOneStaffNotice(member.getMemberId());

        return new CoverNoticeThumbnailResDto(notReadCoverNotice, oneCoverNotice);
    }

    @Transactional(readOnly = true)
    public List<CoverNoticeBoxResDto> readCoverNoticeSpec(LoginMember member){
        // #정책 없음 일단 페징 처리 나중에
        List<CoverNoticeBoxResDto> staffNoticeList = staffCoverNoticeSpecRepository.getStaffNoticeSpec(member.getMemberId());
        return staffNoticeList;
    }
}
