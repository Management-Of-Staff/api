package com.example.sidepot.work.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StoreWorkerResDto {

    private Long staffId;
    private String staffName;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<RequestedCoverStaff> requestedCoverStaffList;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RequestedCoverStaff{
        private Long requestedStaffId;
        private String requestedStaffName;
        private LocalTime coverStartTime;
        private LocalTime coverEndTime;
    }
}
