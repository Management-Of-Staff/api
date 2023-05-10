package com.example.sidepot.work.app;

import com.example.sidepot.employment.domain.Employment;
import com.example.sidepot.employment.domain.EmploymentRepository;
import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.member.domain.Staff;
import com.example.sidepot.member.domain.StaffRepository;
import com.example.sidepot.store.domain.Store;
import com.example.sidepot.store.domain.StoreRepository;
import com.example.sidepot.work.domain.WorkTime;
import com.example.sidepot.work.domain.WorkTimeRepository;
import com.example.sidepot.work.dto.WorkRequestDto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class WorkCommandService {

    private final EmploymentRepository employmentRepository;
    private final WorkTimeRepository workTimeRepository;
    private final StoreRepository storeRepository;
    private final StaffRepository staffRepository;

    @Transactional
    public void createWorkSchedule(LoginMember member, createWorkReqDto createWorkReqDto){
        //매장과 직원 검증
        Store storePs = storeRepository.findById(createWorkReqDto.getStoreId())
                .orElseThrow(() -> new IllegalStateException("찾는 매장이 없습니다."));
        Staff staffPs = staffRepository.findById(member.getMemberId()).get();

        //TODO 근무추가 여부 검증
        /* 코드 */

        //TODO 빌더를 생성자로 변경
        //근무 생성
        List<WorkTime> workTimeList = createWorkReqDto.getDayOfWeekList().stream()
                .map(day -> WorkTime.builder()
                        .startTime(createWorkReqDto.getStartTime())
                        .endTime(createWorkReqDto.getEndTime())
                        .dayOfWeek(day)
                        .staff(staffPs)
                        .store(storePs)
                        .build())
                .collect(Collectors.toList());
        workTimeRepository.saveAll(workTimeList);
    }

    @Transactional
    public void updateWorkSchedule(LoginMember member, updateWorkReqDto updateWorkReqDto){
        //TODO 사실상 대타가 있으면 함부로 변경 불가
        //매장과 직원 검증 - 삭제 후 추가 방법
        workTimeRepository.deleteAllById(updateWorkReqDto.getWorkTimeIds());
        this.createWorkSchedule(member, updateWorkReqDto.getCreateWorkReqDto());
    }

    @Transactional
    public void deleteWorkSchedule(LoginMember member, deleteWorkReqDto deleteWorkReqDto){
        workTimeRepository.deleteAllById(deleteWorkReqDto.getWorkTimeIds());
    }

    private void isPossibleWork(createWorkReqDto createWorkReqDto, List<Employment> employmentList) {
        employmentList.forEach(
                e -> e.checkOverlappingSchedule(
                        createWorkReqDto.getDayOfWeekList(),
                        createWorkReqDto.getStartTime(),
                        createWorkReqDto.getEndTime()));
    }
}
