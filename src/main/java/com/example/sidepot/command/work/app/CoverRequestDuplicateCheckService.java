package com.example.sidepot.command.work.app;


import com.example.sidepot.command.work.domain.CoverWork;
import com.example.sidepot.command.work.dto.CoverWorkRequestDto.CreateCoverWorkReqDto;
import com.example.sidepot.command.work.repository.CoverWorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CoverRequestDuplicateCheckService {

    private final CoverWorkRepository coverWorkRepository;

    /**
     * 해당 날짜(들)에 이미 요청이 생성되어 있으면 리스트에 담아서 반환한다.
     * 1 고정근무 : N 변동근무면 복잡해짐 현재는 1:1관계
     */
    @Transactional(readOnly = true)
    public List<CoverWork> checkRequestDuplicate(List<CreateCoverWorkReqDto> createCoverWorkReqDtoList){

        List<CoverWork> coverWorkList = new ArrayList<>();
        for(CreateCoverWorkReqDto dto : createCoverWorkReqDtoList){
            coverWorkRepository.
                    findByWorkTime_WorkTimeIdAndCoverDateTime_CoverDate(dto.getWorkTimeId(), dto.getCoverDate())
                    .ifPresent(cw -> coverWorkList.add(cw));
        }
        return coverWorkList;
    }
}
