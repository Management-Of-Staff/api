package com.example.sidepot.command.work.app;

import com.example.sidepot.command.employment.domain.Employment;
import com.example.sidepot.command.employment.repository.EmploymentRepository;
import com.example.sidepot.command.member.domain.Staff;
import com.example.sidepot.command.member.domain.StaffRepository;
import com.example.sidepot.command.notification.domain.NoticeType;
import com.example.sidepot.command.work.domain.*;
import com.example.sidepot.command.work.repository.query.CoverWorkDaoRepository;
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
    private final CoverWorkDaoRepository coverWorkDaoRepository;
    private final EmploymentRepository employmentRepository;


    /**
     * 대타 요청 생성
     * 비지니스 로직 - 대타 알림함이 일부 대타의 생명 주기를 관리함
     */
    @Transactional
    public void createCoverManager(LoginMember member,
                                   List<CreateCoverWorkReqDto> createCwReqDtoList){
        // 중복 대타 요청 여부
        validRequests(createCwReqDtoList);

        //요청자 조회
        Staff requestedStaff = findStaff(member);

        //매장 별로 요청 매핑
        Map<Long, List<CreateCoverWorkReqDto>> byStore
                = createCwReqDtoList.stream().collect(Collectors.groupingBy(dto -> dto.getStoreId()));

        // 대타 매니저 && 대타 생성
        List<CoverManager> newCmList = createCmList(requestedStaff, byStore);

        // 대타 알림함 생성 && 셋팅
        setCoverNotice(newCmList);

        //공통 알림으로 위임
        Events.raise(new CoverWorkRequestedEvent(newCmList));

        //저장
        coverManagerRepository.saveAll(newCmList);
    }

    private void validRequests(List<CreateCoverWorkReqDto> createCoverWorkReqDtoList){
        List<CoverWork> coverWorkList = checkRequestDuplicate(createCoverWorkReqDtoList);
        if(!coverWorkList.isEmpty()){
            throw new IllegalStateException("겹치는 날짜의 요청이 있습니다.");
        }
        //겹치는 날짜를 보여줄 코드, 필요하면
    }

    private List<CoverWork> checkRequestDuplicate(List<CreateCoverWorkReqDto> createCoverWorkReqDtoList){

        List<CoverWork> coverWorkList = new ArrayList<>();
        for(CreateCoverWorkReqDto dto : createCoverWorkReqDtoList){
            coverWorkDaoRepository.findCwByWtIdOnDay(dto.getWorkTimeId(), dto.getCoverDate())
                    .ifPresent(cw -> coverWorkList.add(cw));
        }
        return coverWorkList;
    }

    private List<CoverManager> createCmList(Staff requestedStaff, Map<Long, List<CreateCoverWorkReqDto>> byStore) {
        List<CoverManager> newCmList = new ArrayList<>();
        for (Map.Entry<Long, List<CreateCoverWorkReqDto>> entry : byStore.entrySet()){
            Store storePs = findStore(entry);
            CoverManager coverManager = CoverManager.newCoverManager(requestedStaff, storePs);
            setCoverWork(requestedStaff, entry, coverManager);
            newCmList.add(coverManager);
        }
        return newCmList;
    }

    private void setCoverWork(Staff requestedStaff, Map.Entry<Long, List<CreateCoverWorkReqDto>> entry, CoverManager coverManager) {
        for(CreateCoverWorkReqDto createCwReqDto : entry.getValue()){
            WorkTime wtPs = findWorkTime(createCwReqDto);
            CoverWork coverWork = CoverWork.newCoverWork(requestedStaff, createCwReqDto, wtPs);
            coverManager.addCoverWork(coverWork);
        }
    }


    private void setCoverNotice(List<CoverManager> coverManagerList){
        for (CoverManager coverManager : coverManagerList) {
            List<Employment> employmentList = findAllEmploymentByStore(coverManager);
            Sender sender = new Sender(coverManager.getRequestedStaff().getId(), coverManager.getRequestedStaff().getName());
            setCoverNoticeList(coverManager, employmentList, sender);
        }
    }

    private void setCoverNoticeList(CoverManager coverManager, List<Employment> employmentList, Sender sender) {
        for (Employment employment : employmentList) {
            if(!(employment.getStaff().getMemberId().equals(coverManager.getRequestedStaff().getId()))) {
                Receiver receiver = new Receiver(employment.getStaff());
                CoverNotice coverNotice = CoverNotice.newCoverNotice(sender, receiver, NoticeType.REQUESTED);
                coverManager.addCoverNotice(coverNotice);
            }
        }
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

    private List<Employment> findAllEmploymentByStore(CoverManager coverManager) {
        List<Employment> empList = employmentRepository.findAllByStore_StoreId(coverManager.getStoreInfo().getStoreId());
        return empList;
    }
}
