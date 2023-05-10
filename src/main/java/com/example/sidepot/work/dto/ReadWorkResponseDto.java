package com.example.sidepot.work.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class ReadWorkResponseDto {

    @Getter
    public static class ReadWorkByStoreResDto{
        private Long storeId;
        private String storeName;
        private Long workTimeId;
        private LocalTime startTime;
        private LocalTime endTime;
        private DayOfWeek day;

        public ReadWorkByStoreResDto(Long storeId, String storeName, Long workTimeId, LocalTime startTime, LocalTime endTime, DayOfWeek day) {
            this.storeId = storeId;
            this.storeName = storeName;
            this.workTimeId = workTimeId;
            this.startTime = startTime;
            this.endTime = endTime;
            this.day = day;
        }
    }
}
