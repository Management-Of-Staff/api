package com.example.sidepot.work.dto;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class CoverWorkRequestDto {

    @Getter
    public static class CreateCoverWorkReqDto{
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
