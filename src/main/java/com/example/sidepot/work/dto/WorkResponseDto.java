package com.example.sidepot.work.dto;

import com.example.sidepot.work.domain.CoverWork;
import com.example.sidepot.work.domain.WorkTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class WorkResponseDto {

    @Getter
    @AllArgsConstructor
    public static class ReadWorkByStoreResDto {
        private Long storeId;
        private String branchName;
        private String storeName;
        private Long workTimeId;
        private LocalTime startTime;
        private LocalTime endTime;
        private DayOfWeek day;
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
            //this.coveredWorkList = workTime.getCoverWorkBetween(startDate, endDate);
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
            this.acceptedStaffId = coverWork.getAcceptedStaff().getAcceptedStaffId();
            this.acceptedStaffName = coverWork.getAcceptedStaff().getAcceptedStaffName();
            this.coverDate = coverWork.getCoverDateTime().getCoverDate();
        }
    }
}
