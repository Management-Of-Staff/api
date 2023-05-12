package com.example.sidepot.work.app;


import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.work.domain.*;
import com.example.sidepot.work.dto.CoverWorkRequestDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CoverWorkCommandService {

    private final WorkTimeRepository workTimeRepository;
    private final CoverWorkRepository coverWorkRepository;
    private final CoverWorkNoticeRepository coverWorkNoticeRepository;

    @Transactional
    public void createCoverWorkRequest(LoginMember member, List<CreateCoverWorkReqDto> createCoverWorkReqDtoList) {

    }

    @Transactional
    public CoverWorkNotice createCoverWorkNotification(CreateCoverWorkReqDto createCoverWorkReqDto) {
        return coverWorkNoticeRepository.save(new CoverWorkNotice(createCoverWorkReqDto));
    }

    @Transactional
    public void acceptCoverWorkRequest(LoginMember member, List<AcceptCoverWorkReqDto> acceptCoverWorkReqDtoList) {
        List<CoverWork> coverWorkPsList = coverWorkRepository.findAllById(acceptCoverWorkReqDtoList.stream()
                .map(o -> o.getCoverWorkTimeId()).collect(Collectors.toList()));

        coverWorkPsList.forEach(o -> o.coverWorkAccepted(member));
        coverWorkRepository.saveAll(coverWorkPsList);
    }


}
