package com.example.sidepot.work.app;

import com.example.sidepot.global.event.Events;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.member.domain.Staff;
import com.example.sidepot.member.domain.StaffRepository;
import com.example.sidepot.notification.work.domain.NoticeType;
import com.example.sidepot.store.domain.Store;
import com.example.sidepot.store.domain.StoreRepository;
import com.example.sidepot.work.domain.*;
import com.example.sidepot.work.domain.SenderId;
import com.example.sidepot.work.domain.StoreId;
import com.example.sidepot.work.dto.CoverWorkRequestDto.CreateCoverWorkReqDto;
import com.example.sidepot.work.event.CoverWorkRequestedEvent;

import com.example.sidepot.work.repository.CoverManagerRepository;
import com.example.sidepot.work.repository.WorkTimeRepository;
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
     * @param loginMember
     * @param createCwReqDtoList
     *
     */
    @Transactional
    public void requestCoverWork(LoginMember loginMember,
                                 List<CreateCoverWorkReqDto> createCwReqDtoList) {

        checkRequest(createCwReqDtoList);

        Optional<Staff> staffOp = staffRepository.findById(loginMember.getMemberId());
        Staff requestedStaff = staffOp.orElseThrow();

        Map<Long, List<CreateCoverWorkReqDto>> byStore
                = createCwReqDtoList.stream().collect(Collectors.groupingBy(dto -> dto.getStoreId()));

        List<CoverManager> coverManagerList = new ArrayList<>();
        for (Map.Entry<Long, List<CreateCoverWorkReqDto>> entry : byStore.entrySet()) {
            List<CoverWork> coverWorkList = createCoverWorkList(entry.getValue(), requestedStaff);
            Optional<Store> storeOp = storeRepository.findById(entry.getKey());
            Store storePs = storeOp.orElseThrow();
            coverManagerList.add(new CoverManager(
                    NoticeType.REQUESTED.getMessage(),
                    new StoreId(storePs.getStoreId(), storePs.getBranchName(), storePs.getStoreName()),
                    new SenderId(requestedStaff.getMemberId(), requestedStaff.getMemberName()),
                    coverWorkList));
        }

        Events.raise(new CoverWorkRequestedEvent(coverManagerList, byStore.keySet()));
        coverManagerRepository.saveAll(coverManagerList);
    }

    private void checkRequest(List<CreateCoverWorkReqDto> createCoverWorkReqDtoList){
        List<CoverWork> coverWorkList = coverRequestDuplicateCheckService.checkRequestDuplicate(createCoverWorkReqDtoList);
        if(!coverWorkList.isEmpty() || coverWorkList != null){
            throw new IllegalStateException("겹치는 날짜의 요청이 있습니다.");
        }
        //겹치는 날짜를 보여줄 코드, 필요하면
    }

    private List<CoverWork> createCoverWorkList(List<CreateCoverWorkReqDto> createCwReqDtoList,
                                                Staff requestedStaff) {

        List<CoverWork> coverWorkList = new ArrayList<>();
        for (CreateCoverWorkReqDto createCwReqDto : createCwReqDtoList) {
            Optional<WorkTime> wtOp = workTimeRepository.findById(createCwReqDto.getWorkTimeId());
            WorkTime wtPs = wtOp.orElseThrow();
            coverWorkList.add(new CoverWork(
                    new CoverDateTime(createCwReqDto.getCoverDate(), wtPs.getStartTime(), wtPs.getEndTime()),
                    new OriginWorkId(wtPs.getWorkTimeId()),
                    new RequestedStaffId(requestedStaff.getMemberId(), requestedStaff.getMemberName(), requestedStaff.getUuid())));
        }
        return coverWorkList;
    }
}
