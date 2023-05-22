package com.example.sidepot.work.app;

import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.member.domain.Staff;
import com.example.sidepot.member.domain.StaffRepository;
import com.example.sidepot.store.domain.Store;
import com.example.sidepot.store.domain.StoreRepository;
import com.example.sidepot.work.domain.CoverWork;
import com.example.sidepot.work.domain.WorkTime;
import com.example.sidepot.work.dto.WorkRequestDto.*;
import com.example.sidepot.work.repository.CoverWorkRepository;
import com.example.sidepot.work.repository.WorkTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WorkTimeCommandService {

    private final WorkTimeRepository workTimeRepository;
    private final CoverWorkRepository coverWorkRepository;
    private final WorkPossibleCheckService workPossibleCheckService;
    private final StaffRepository staffRepository;
    private final StoreRepository storeRepository;
    @Transactional
    public void createWorkTime(LoginMember member, CreateWorkReqDto createWorkReqDto){
        //직접참조 필드 조회
        Optional<Staff> staffOp = staffRepository.findById(member.getMemberId());
        Staff staffPS = staffOp.orElseThrow();
        Optional<Store> storeOp = storeRepository.findById(createWorkReqDto.getStoreId());
        Store storePs = storeOp.orElseThrow();

        //생성할 근무
        List<WorkTime> newWorkTimeList = new ArrayList<>();
        for(DayOfWeek day : createWorkReqDto.getDayOfWeekList()){
            newWorkTimeList.add(new WorkTime(
                    staffPS, storePs,
                    createWorkReqDto.getStartTime(),
                    createWorkReqDto.getEndTime(), day));
        }

        //비교할 모든 근무
        List<WorkTime> workTimePsList = workTimeRepository.findAllByStaff(staffPS); // 직접참조
        List<CoverWork> coverWorkPsList = coverWorkRepository.findAllByAcceptedStaff_AcceptedStaffId(member.getMemberId());// 간접참조

        //생성할 근무와 모든 근무 비교
        //workPossibleCheckService.workabilityScheduleCheck(newWorkTimeList, workTimePsList, coverWorkPsList);

        // 검증되면 저장
        workTimeRepository.saveAll(newWorkTimeList);
    }

    @Transactional
    public void deleteWorkTime(LoginMember member, DeleteWorkReqDto deleteWorkReqDto){
        List<WorkTime> workTimePsList = workTimeRepository.findAllById(deleteWorkReqDto.getWorkTimeIds());


        //삭제 가능한 근무인가? -> 수락한 대타가 있다던가
        LocalDate nowDate = LocalDate.now();
        for(WorkTime workTime : workTimePsList){
            List<CoverWork> coverWorkList = coverWorkRepository
                    .findByWorkTime_WorkTimeIdAndCoverDateTime_CoverDateAfter(workTime.getWorkTimeId(), nowDate);

            if(!coverWorkList.isEmpty()) throw new IllegalArgumentException("삭제할 근무와 관련된 대타 근무가 있습니다.");
        }

        //경우의 수가 너무 복잡하고 기획도 없어서 삭제처리
        workTimePsList.stream().forEach(wt -> wt.delete());
    }
    @Transactional
    public void updateWorTime(LoginMember member, UpdateWorkReqDto updateWorkReqDto){
        deleteWorkTime(member, updateWorkReqDto.getDeleteWorkReqDto());
        createWorkTime(member, updateWorkReqDto.getCreateWorkReqDto());
    }
}
