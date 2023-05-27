package com.example.sidepot.command.work.dto;

import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public class WorkRequestDto {
    @Getter
    public static class CreateWorkReqDto {
        private Long storeId;
        private Long staffId;
        private LocalTime startTime;
        private LocalTime endTime;
        private List<DayOfWeek> dayOfWeekList;
    }
    @Getter
    public static class DeleteWorkReqDto {
        private Long workManagerId;
    }
    @Getter
    public static class UpdateWorkReqDto {
        private DeleteWorkReqDto deleteWorkReqDto;
        private CreateWorkReqDto createWorkReqDto;
    }
}
