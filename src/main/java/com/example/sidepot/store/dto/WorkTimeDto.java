package com.example.sidepot.store.dto;

import com.example.sidepot.work.domain.WorkTime;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
public class WorkTimeDto {
    private String workDays;
    private LocalTime startTime;
    private LocalTime endTime;

    @Builder
    private WorkTimeDto(String workDays, LocalTime startTime, LocalTime endTime) {
        this.workDays = workDays;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static WorkTimeDto from(WorkTime workTime) {
        return WorkTimeDto.builder()
                .workDays(workTime.getWorkDays())
                .startTime(workTime.getStartTime())
                .endTime(workTime.getEndTime())
                .build();
    }

    public static List<WorkTimeDto> fromList(List<WorkTime> workTimes) {
        return workTimes.stream()
                .map(WorkTimeDto::from)
                .collect(toList());
    }
}
