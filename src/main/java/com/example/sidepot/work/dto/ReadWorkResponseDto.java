package com.example.sidepot.work.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class ReadWorkResponseDto {

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
}
