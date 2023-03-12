package com.example.sidepot.store.dto;

import com.example.sidepot.work.domain.WeekWorkTime;
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

    public static WorkTimeDto from(WeekWorkTime weekWorkTime) {
        return WorkTimeDto.builder()
                .workDays(weekWorkTime.getWorkDays())
                .startTime(weekWorkTime.getStartTime())
                .endTime(weekWorkTime.getEndTime())
                .build();
    }

    public static List<WorkTimeDto> fromList(List<WeekWorkTime> weekWorkTimes) {
        return weekWorkTimes.stream()
                .map(WorkTimeDto::from)
                .collect(toList());
    }
}
