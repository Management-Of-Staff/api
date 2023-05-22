package com.example.sidepot.work.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StaffCoverSchedule {
    private Long coverWorkId;
    private Long storeId;
    private String branchName;
    private String storeName;
    private LocalDate coverDate;
    private LocalTime startTime;
    private LocalTime endTime;
}
