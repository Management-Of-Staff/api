package com.example.sidepot.command.work.app;

import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.command.work.domain.StaffCoverNoticeBox;
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
        StaffCoverNoticeBox oneStaffCoverNoticeBox = staffCoverNoticeSpecRepository.getOneStaffNotice(member.getMemberId());

        return new CoverNoticeThumbnailResDto(notReadCoverNotice, oneStaffCoverNoticeBox);
    }

    @Transactional(readOnly = true)
    public List<CoverNoticeBoxResDto> readCoverNoticeSpec(LoginMember member){
        //정책 없음 그냥 싹다 반환
        List<CoverNoticeBoxResDto> staffNoticeList = staffCoverNoticeSpecRepository.getStaffNoticeSpec(member.getMemberId());
        return staffNoticeList;
    }
}
