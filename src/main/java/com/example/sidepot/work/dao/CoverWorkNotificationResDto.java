package com.example.sidepot.work.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class CoverWorkNotificationResDto {

    private boolean isAccepted;
    private Long storeId;
    private String storeName;
    private Long requestedStaffId;
    private String requestedStaffName;
    private LocalDate coverDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Long workingTime;

    public CoverWorkNotificationResDto(boolean isAccepted, Long storeId, String storeName, Long requestedStaffId,
                                       String requestedStaffName, LocalDate coverDate, LocalTime startTime, LocalTime endTime) {
        this.isAccepted = isAccepted;
        this.storeId = storeId;
        this.storeName = storeName;
        this.requestedStaffId = requestedStaffId;
        this.requestedStaffName = requestedStaffName;
        this.coverDate = coverDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workingTime = Duration.between(startTime, endTime).toHours();
    }
}
