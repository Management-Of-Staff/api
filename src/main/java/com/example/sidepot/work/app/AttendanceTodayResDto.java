package com.example.sidepot.work.app;

import com.example.sidepot.work.domain.CoverWork;
import com.example.sidepot.work.domain.WorkTime;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AttendanceTodayResDto {

    private List<ToDaysWork> toDaysWorkList;
    private List<ToDaysCover> toDaysCoverList;

    public AttendanceTodayResDto(List<WorkTime> workTimeList, List<CoverWork> coverWorkList) {
        this.toDaysWorkList = new ArrayList<>();
        this.toDaysCoverList = new ArrayList<>();

        //직참조 되어 있는 엔티티라 여기서 N+1 날라감, 페치조인 불가능
        if (workTimeList != null || !workTimeList.isEmpty()) {
            this.toDaysWorkList.addAll(workTimeList.stream().map(wt -> new ToDaysWork(wt)).collect(Collectors.toList()));
        }

        if (coverWorkList != null || !coverWorkList.isEmpty()) {
            this.toDaysCoverList.addAll(coverWorkList.stream().map(cw -> new ToDaysCover(cw)).collect(Collectors.toList()));
        }
    }

    @Getter
    public static class ToDaysWork{
        private Long workTimeId;
        private Long storeId;
        private String branchName;
        private String storeName;
        private DayOfWeek dayOfWeek;
        private LocalTime startTime;
        private LocalTime endTime;

        public ToDaysWork(WorkTime workTime) {
            this.workTimeId = workTime.getWorkTimeId();
            this.storeId = workTime.getStore().getStoreId();
            this.branchName = workTime.getStore().getBranchName();
            this.storeName = workTime.getStore().getStorePhone();
            this.dayOfWeek = workTime.getDayOfWeek();
            this.startTime = workTime.getStartTime();
            this.endTime = workTime.getEndTime();
        }
    }

    @Getter
    public static class ToDaysCover{
        private Long coverWorkId;
        private Long storeId;
        private String branchName;
        private String storeName;
        private LocalDate coverDate;
        private LocalTime startTime;
        private LocalTime endTime;

        public ToDaysCover(CoverWork coverWork) {
            this.coverWorkId = coverWork.getCoverWorkId();
            this.storeId = coverWork.getCoverManager().getStoreId().getStoreId();
            this.branchName = coverWork.getCoverManager().getStoreId().getBranchName();
            this.storeName = coverWork.getCoverManager().getStoreId().getStoreName();
            this.coverDate = coverWork.getCoverDateTime().getCoverDate();
            this.startTime = coverWork.getCoverDateTime().getStartTime();
            this.endTime = coverWork.getCoverDateTime().getEndTime();
        }
    }
}