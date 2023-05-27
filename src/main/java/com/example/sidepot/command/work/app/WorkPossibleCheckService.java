package com.example.sidepot.command.work.app;


import com.example.sidepot.global.DomainService;
import com.example.sidepot.command.work.domain.CoverWork;
import com.example.sidepot.command.work.domain.WorkTime;

import java.util.List;

@DomainService
public class WorkPossibleCheckService {


    public void coverWorkabilityScheduleCheck(List<CoverWork> reqCoverWorkList, //수락할 대타 근무일정
                                              List<WorkTime> staffWorkTimeListPs, // 수락할 직원의 고정근무일정
                                              List<CoverWork> staffAcceptedCoverWorkPs, //수락할 직원의 대타 일정
                                              List<CoverWork> staffRequestedCoverWorkPs) { // 수락할 직원의 비어이 있는 고정근무 일정
        boolean isOverLapped = false;
        boolean isEmptyWork = false;

        //수락할 대타 근무가
        for (CoverWork coverWork : reqCoverWorkList) {

            // 고정 근무와 겹치는가?
            for (WorkTime workTime : staffWorkTimeListPs) {
                if (workTime.isOverlappedWithCover(coverWork.getCoverDateTime())) {
                    isOverLapped = true;
                    break;
                }
            }

            // 요청한 대타 근무가 모두 수락되어 고정 근무가 비어 있는가?
            if (isOverLapped) {
                for (CoverWork rCoverWork : staffRequestedCoverWorkPs) {
                    if (rCoverWork.isOverlapped(coverWork.getCoverDateTime())) {
                        isEmptyWork = true;
                        isOverLapped = false;

                    } else {
                        isEmptyWork = false;
                        isOverLapped = true;
                    }
                }
            }
            //이전에 수락한 대타 근무가 겹치는가?
            if (isEmptyWork) {
                for (CoverWork aCoverWork : staffAcceptedCoverWorkPs) {
                    if (aCoverWork.isOverlapped(coverWork.getCoverDateTime())) {
                        isOverLapped = true;
                    }
                }
            }
            if (isOverLapped) {
                throw new IllegalStateException("겹치는 근무가 있긴 있는데,,,");
            }
        }
    }

    // 겹치는 근무를 요구할 경우 리스트에 담아서
    public void workabilityScheduleCheck(List<WorkTime> newWtList,
                                         List<WorkTime> prevWtList,
                                         List<CoverWork> cwList) {
        //추가할 근무가
        for (WorkTime newWt : newWtList) {
            for (WorkTime preWt : prevWtList) {
                if (newWt.isOverlapped(preWt.getStartTime(), preWt.getEndTime(), preWt.getDayOfWeek())) {
                    throw new IllegalStateException("겹치는 근무가 있습니다.");
                }
            }

            //겹치는 대타 근무가 있는가?
            for (CoverWork coverWork : cwList) {
                if (newWt.isOverlappedWithCover(coverWork.getCoverDateTime())) {
                    throw new IllegalStateException("겹치는 대타 근무가 있습니다.");
                }
            }
        }
    }
}

