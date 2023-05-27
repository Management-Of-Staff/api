package com.example.sidepot.command.work.app;

import com.example.sidepot.command.attendance.domain.WorkerId;
import com.example.sidepot.command.member.domain.Staff;
import com.example.sidepot.command.member.domain.StaffRepository;
import com.example.sidepot.command.store.domain.Store;
import com.example.sidepot.command.store.domain.StoreRepository;
import com.example.sidepot.command.work.domain.CoverWork;
import com.example.sidepot.command.work.domain.StoreInfo;
import com.example.sidepot.command.work.domain.WorkManager;
import com.example.sidepot.command.work.domain.WorkTime;
import com.example.sidepot.command.work.dto.WorkRequestDto.*;
import com.example.sidepot.command.work.repository.CoverWorkRepository;
import com.example.sidepot.command.work.repository.WorkManagerRepository;
import com.example.sidepot.command.work.repository.WorkTimeRepository;
import com.example.sidepot.command.work.repository.query.CoverWorkDaoRepository;
import com.example.sidepot.command.work.repository.query.WorkTimeDaoRepository;
import com.example.sidepot.global.security.LoginMember;
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
    private final CoverWorkDaoRepository coverWorkDaoRepository;
    private final WorkTimeDaoRepository workTimeDaoRepository;
    private final CoverWorkRepository coverWorkRepository;
    private final StaffRepository staffRepository;
    private final StoreRepository storeRepository;
    private final WorkManagerRepository workManagerRepository;
    private final WorkPossibleCheckService workPossibleCheckService;

    /**
     * 근무 생성
     * @param member
     * @param createWorkReqDto
     */
    @Transactional
    public void createWorkTime(LoginMember member, CreateWorkReqDto createWorkReqDto) {
        //매장내에 있는 직원인지 검증

        //직접참조 필드 조회
        Staff staffPS = findStaffById(createWorkReqDto.getStaffId());
        Store storePs = findStoreById(createWorkReqDto.getStoreId());

        //요청 받은 생성할 근무
        List<WorkTime> newWtList = createNewWork(createWorkReqDto, staffPS, storePs);

        //비교할 모든 근무 조회
        List<WorkTime> prevWtListPs = findWtByStaff(staffPS);
        List<CoverWork> cwListPs = findCwByStaff(createWorkReqDto.getStaffId());

        //생성할 근무와 모든 근무 비교
        workPossibleCheckService.workabilityScheduleCheck(newWtList, prevWtListPs, cwListPs);

        // 검증되면 매니저 생성
        WorkManager workManager = createNewWorkManager(staffPS, storePs, newWtList);

        // 검증되면 저장
        workManagerRepository.save(workManager);
    }
    /**
     * 근무 삭제 처리
     * @param member
     * @param deleteWorkReqDto
     */
    @Transactional
    public void deleteWorkTime(LoginMember member, DeleteWorkReqDto deleteWorkReqDto) {
        WorkManager workManagerPs = findWmByIdFetch(deleteWorkReqDto);
        canBeDelete(workManagerPs);
        workManagerPs.delete();
    }
   /**
     * 근무 수정 -> 삭제 + 재생성
     * @param member
     * @param updateWorkReqDto
     */
    @Transactional
    public void updateWorTime(LoginMember member, UpdateWorkReqDto updateWorkReqDto) {
        deleteWorkTime(member, updateWorkReqDto.getDeleteWorkReqDto());
        createWorkTime(member, updateWorkReqDto.getCreateWorkReqDto());
    }

    // #도메인 서비스
    private static List<WorkTime> createNewWork(CreateWorkReqDto createWorkReqDto, Staff staffPS, Store storePs) {
        List<WorkTime> newWtList = new ArrayList<>();
        for (DayOfWeek day : createWorkReqDto.getDayOfWeekList()) {
            newWtList.add(
                    new WorkTime(
                            staffPS, storePs,
                            createWorkReqDto.getStartTime(),
                            createWorkReqDto.getEndTime(), day));
        }
        return newWtList;
    }
    // #도메인 서비스
    private  WorkManager createNewWorkManager(Staff staffPS, Store storePs, List<WorkTime> newWtList) {
        WorkManager workManager = new WorkManager(
                new StoreInfo(storePs.getStoreId(), storePs.getBranchName(), storePs.getStoreName()),
                new WorkerId(staffPS.getMemberId(), staffPS.getMemberName()),
                newWtList);
        return workManager;
    }
    // #유틸
    private Store findStoreById(Long storeId) {
        Optional<Store> storeOp = storeRepository.findById(storeId);
        return storeOp.orElseThrow();
    }
    // #유틸
    private Staff findStaffById(Long staffId) {
        Optional<Staff> staffOp = staffRepository.findById(staffId);
        Staff staffPS = staffOp.orElseThrow();
        return staffPS;
    }
    // #유틸
    private List<CoverWork> findCwByStaff(Long staffId) {
        List<CoverWork> cwPsList = coverWorkRepository.findAllByAcceptedStaff_Id(staffId);// #DAO, 간접참조
        if (cwPsList == null) {
            cwPsList = new ArrayList<>();
        }
        return cwPsList;
    }
    // #유틸
    private List<WorkTime> findWtByStaff(Staff staffPS) {
        List<WorkTime> wtListPs = workTimeDaoRepository.findWtByStaff(staffPS.getMemberId());
        if (wtListPs == null) {
            wtListPs = new ArrayList<>();
        }
        return  wtListPs;
    }
    // #도메인 서비스
    private void canBeDelete(WorkManager workManagerPs) {
        for (WorkTime workTime : workManagerPs.getWorkTimeList()) {
            if (doHaveAnyCover(workTime)) {
                throw new IllegalStateException("삭제하고 하는 근무에 일정이 남아 있는 대타가 포함되어 있습니다. 확인해주세요");
            }
        }
    }
    // #도메인 서비스
    private boolean doHaveAnyCover(WorkTime workTime) {
        List<CoverWork> coverWorkList = findCoverAfterDayWithWorkId(workTime);
        return !coverWorkList.isEmpty();
    }
    // #유틸
    private List<CoverWork> findCoverAfterDayWithWorkId(WorkTime workTime) {
        return coverWorkDaoRepository.findCoverAfterDayWithWorkId(workTime.getWorkTimeId(), LocalDate.now());
    }
    // #유틸
    private WorkManager findWmByIdFetch(DeleteWorkReqDto deleteWorkReqDto) {
        Optional<WorkManager> workManagerOp = workManagerRepository.findById(deleteWorkReqDto.getWorkManagerId());
        WorkManager workManagerPs = workManagerOp.orElseThrow(); // #error
        return workManagerPs;
    }
}
