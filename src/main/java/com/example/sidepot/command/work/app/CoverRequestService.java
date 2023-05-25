package com.example.sidepot.command.work.app;

import com.example.sidepot.command.member.domain.Staff;
import com.example.sidepot.command.member.domain.StaffRepository;
import com.example.sidepot.command.work.domain.CoverManager;
import com.example.sidepot.command.work.domain.CoverWork;
import com.example.sidepot.command.work.domain.WorkTime;
import com.example.sidepot.global.event.Events;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.command.store.domain.Store;
import com.example.sidepot.command.store.domain.StoreRepository;
import com.example.sidepot.command.work.dto.CoverWorkRequestDto.CreateCoverWorkReqDto;
import com.example.sidepot.command.work.event.CoverWorkRequestedEvent;

import com.example.sidepot.command.work.repository.CoverManagerRepository;
import com.example.sidepot.command.work.repository.WorkTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CoverRequestService {

    private final WorkTimeRepository workTimeRepository;
    private final StoreRepository storeRepository;
    private final StaffRepository staffRepository;
    private final CoverManagerRepository coverManagerRepository;
    private final CoverRequestDuplicateCheckService coverRequestDuplicateCheckService;

    /**
     * 대타 요청 생성
     */
    @Transactional
    public void requestCoverWork(LoginMember loginMember,
                                 List<CreateCoverWorkReqDto> createCwReqDtoList) {

        //중복된 요청이 여부
        validRequests(createCwReqDtoList);

        //요청자 조회
        Staff requestedStaff = findStaff(loginMember);

        //매장 마다 요청 매핑 변수 생성
        Map<Long, List<CreateCoverWorkReqDto>> byStore
                = createCwReqDtoList.stream().collect(Collectors.groupingBy(dto -> dto.getStoreId()));

        //매장 마다 요청 매핑
        List<CoverManager> coverManagerList = new ArrayList<>();
        for (Map.Entry<Long, List<CreateCoverWorkReqDto>> entry : byStore.entrySet()) {
            List<CoverWork> coverWorkList = createCoverWorkList(entry.getValue(), requestedStaff);
            Store storePs = findStore(entry);
            coverManagerList.add(CoverManager.newCoverManager(requestedStaff, coverWorkList, storePs));
        }
        //알림 생성 위임
        Events.raise(new CoverWorkRequestedEvent(coverManagerList, byStore.keySet()));
        coverManagerRepository.saveAll(coverManagerList);
    }

    private List<CoverWork> createCoverWorkList(List<CreateCoverWorkReqDto> createCwReqDtoList,
                                                Staff requestedStaff) {

        List<CoverWork> coverWorkList = new ArrayList<>();
        for (CreateCoverWorkReqDto createCwReqDto : createCwReqDtoList) {
            WorkTime wtPs = findWorkTime(createCwReqDto);
            coverWorkList.add(CoverWork.newCoverWork(requestedStaff, createCwReqDto, wtPs));
        }
        return coverWorkList;
    }

    private void validRequests(List<CreateCoverWorkReqDto> createCoverWorkReqDtoList){
        List<CoverWork> coverWorkList = coverRequestDuplicateCheckService.checkRequestDuplicate(createCoverWorkReqDtoList);
        if(!coverWorkList.isEmpty()){
            throw new IllegalStateException("겹치는 날짜의 요청이 있습니다.");
        }
        //겹치는 날짜를 보여줄 코드, 필요하면
    }

    private WorkTime findWorkTime(CreateCoverWorkReqDto createCwReqDto) {
        Optional<WorkTime> wtOp = workTimeRepository.findById(createCwReqDto.getWorkTimeId());
        WorkTime wtPs = wtOp.orElseThrow();
        return wtPs;
    }

    private Staff findStaff(LoginMember loginMember) {
        Optional<Staff> staffOp = staffRepository.findById(loginMember.getMemberId());
        Staff requestedStaff = staffOp.orElseThrow();
        return requestedStaff;
    }

    private Store findStore(Map.Entry<Long, List<CreateCoverWorkReqDto>> entry) {
        Optional<Store> storeOp = storeRepository.findById(entry.getKey());
        Store storePs = storeOp.orElseThrow();
        return storePs;
    }
}
