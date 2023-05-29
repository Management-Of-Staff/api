package com.example.sidepot.command.work.dto;

import lombok.Getter;

import java.time.LocalDate;

public class CoverWorkRequestDto {

    @Getter
    public static class CreateCoverWorkReqDto{
        private Long storeId;
        private Long workTimeId;
        private LocalDate coverDate;

        //분할 근무면 쓰일 예정
        /*private LocalTime startTime;
        private LocalTime endTime;*/
    }

    @Getter
    public static class AcceptCoverWorkReqDto{
        private Long coverWorkTimeId;
    }
}