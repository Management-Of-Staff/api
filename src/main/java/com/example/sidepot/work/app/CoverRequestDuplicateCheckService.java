package com.example.sidepot.work.app;


import com.example.sidepot.work.domain.CoverWork;
import com.example.sidepot.work.dto.CoverWorkRequestDto.CreateCoverWorkReqDto;
import com.example.sidepot.work.repository.CoverWorkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CoverRequestDuplicateCheckService {

    private final CoverWorkRepository coverWorkRepository;

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
