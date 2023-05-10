package com.example.sidepot.work.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;


public class ReadStoreWorkerOnDay {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReadWork {
        private Long staffId;
        private String staffName;
        private DayOfWeek day;
        private LocalTime startTime;
        private LocalTime endTime;
        private WorkType workType;
    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReadCoverWork {
        private Long coverWorkId;
        private Long acceptStaffId;
        private Long requestStaffId;
    }



}
