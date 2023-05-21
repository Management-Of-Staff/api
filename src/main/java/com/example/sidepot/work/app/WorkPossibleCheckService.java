package com.example.sidepot.work.app;


import com.example.sidepot.global.DomainService;
import com.example.sidepot.work.domain.CoverWork;
import com.example.sidepot.work.domain.WorkTime;

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
               if(isEmptyWork){
                   for(CoverWork aCoverWork : staffAcceptedCoverWorkPs){
                       if(aCoverWork.isOverlapped(coverWork.getCoverDateTime())){
                           isOverLapped = true;
                       }
                   }
               }
               if(isOverLapped){
                   throw  new IllegalStateException("겹치는 근무가 있긴 있는데,,,");
               }
           }
        }


    public void workabilityScheduleCheck(List<WorkTime> workTimeList, //추가할 고정 근무
                                         List<WorkTime> staffWorkTimeListPs, //기존에 있던 모든 고정 근무
                                         List<CoverWork> staffAcceptedCoverWorkPs) { //수락할 직원의 대타 일정


        //추가할 대타 근무가
        for(WorkTime req : workTimeList){

            //기존 모든 근무와 겹치는가?
            for(WorkTime ori : staffWorkTimeListPs){

            }

            //겹치지 않는 다면
            if(true){

                //겹치는 대타 근무가 있는가?
                for(CoverWork coverWork : staffAcceptedCoverWorkPs){

                }
            }
        }
    }
}
