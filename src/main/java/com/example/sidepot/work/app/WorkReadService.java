package com.example.sidepot.work.app;

import com.example.sidepot.global.security.LoginMember;
import com.example.sidepot.work.dao.ReadStoreWorkerOnDay;
import com.example.sidepot.work.dao.StaffWork;
import com.example.sidepot.work.dao.WorkReadQuery;
import com.example.sidepot.work.dao.WorkType;
import com.example.sidepot.work.domain.CoverWork;
import com.example.sidepot.work.domain.WorkTime;
import com.example.sidepot.work.dto.ReadWorkResponseDto.ReadWorkByStoreResDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class WorkReadService {
    private final WorkReadQuery workReadQuery;

    public Map<List<String>, List<ReadWorkByStoreResDto>> readAllWorkByStore(Long staffId){
        List<ReadWorkByStoreResDto> readWorkByStoreResDtoList = workReadQuery.readAllWorkOfStaff(staffId);

        return readWorkByStoreResDtoList.stream().collect(Collectors.groupingBy(
                o -> Arrays.asList(o.getStoreName())));
    }

    public Map<List<Serializable>, List<StaffWork>> readAllEmployment(Long memberId, Long storeId) {
        return workReadQuery.readAllEmployment(storeId);
    }


    public List<ReadStoreWorkerOnDay.ReadWork> readAllEmploymentOnDay(LoginMember member, Long storeId, LocalDate onDay){
        List<WorkTime> workTimeList = workReadQuery.readAllEmploymentOnDay(member.getMemberId(), storeId, onDay);
        List<ReadStoreWorkerOnDay.ReadWork> readWorkList = new ArrayList<>();
        for(WorkTime workTime : workTimeList){
            for(CoverWork coverWork : workTime.getCoverWorkList()){
                if(coverWork.getCoverDate().equals(onDay)){
                    readWorkList.add(new ReadStoreWorkerOnDay.ReadWork(
                            coverWork.getAcceptedStaffId(),
                            coverWork.getAcceptedStaffName(),
                            coverWork.getDayOfWeek(),
                            coverWork.getStartTime(),
                            coverWork.getEndTime(),
                            WorkType.COVER));
                }
            }
            readWorkList.add(new ReadStoreWorkerOnDay.ReadWork(
                    workTime.getStaff().getMemberId(),
                    workTime.getStaff().getMemberName(),
                    workTime.getDayOfWeek(),
                    workTime.getStartTime(),
                    workTime.getEndTime(),
                    WorkType.DEFAULT));
        }
        return readWorkList;
    }

    public List<WorkWithCoveredResponseDto> readAllWorkOfStaff(Long memberId, int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        List<WorkTime> workTimeList = workReadQuery.readAllCoverWorkOfStaff(memberId);
        return workTimeList.stream().map(o -> new WorkWithCoveredResponseDto(o, startDate, endDate)).collect(Collectors.toList());

    }
    @Getter
    public static class WorkWithCoveredResponseDto{
        private Long workTimeId;
        private Long storeId;
        private Long storeName;
        private LocalTime startTime;
        private LocalTime endTime;
        private List<CoveredWork> coveredWorkList;

        public WorkWithCoveredResponseDto(Long storeId, Long storeName, LocalTime startTime, LocalTime endTime, List<CoveredWork> coveredWorkList) {
            this.storeId = storeId;
            this.storeName = storeName;
            this.startTime = startTime;
            this.endTime = endTime;
            this.coveredWorkList = coveredWorkList;
        }

        public WorkWithCoveredResponseDto(WorkTime workTime, LocalDate startDate, LocalDate endDate) {
            this.workTimeId = workTime.getWorkTimeId();
            this.storeId = workTime.getStore().getStoreId();
            this.storeName = workTime.getStore().getStoreId();
            this.startTime = workTime.getStartTime();
            this.endTime = workTime.getEndTime();
            this.coveredWorkList = workTime.getCoverWorkBetween(startDate, endDate);
        }

    }
    @Getter
    public static class CoveredWork{
        private Long coverWorkTimeId;
        private Long acceptedStaffId;
        private String acceptedStaffName;
        private LocalDate coverDate;

        public CoveredWork(CoverWork coverWork) {
            this.coverWorkTimeId = coverWork.getCoverWorkId();
            this.acceptedStaffId = coverWork.getAcceptedStaffId();
            this.acceptedStaffName = coverWork.getAcceptedStaffName();
            this.coverDate = coverWork.getCoverDate();
        }
    }
}
