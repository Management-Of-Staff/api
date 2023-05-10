package com.example.sidepot.work.dto;

import lombok.Getter;

import java.time.LocalDate;

public class CoverWorkRequestDto {

    @Getter
    public static class CreateCoverWorkReqDto{

        private Long requestedStaffId;
        private Long storeId;
        private Long workTimeId;
        private LocalDate coverDate;

    }

    @Getter
    public static class AcceptCoverWorkReqDto{
        private Long coverWorkTimeId;
        private Long acceptedStaffId;
    }
}
