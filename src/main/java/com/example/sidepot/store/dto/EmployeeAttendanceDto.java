package com.example.sidepot.store.dto;

import com.example.sidepot.store.domain.EmployeeAttendance;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class EmployeeAttendanceDto {
    private String name;
    private String phoneNum;
    List<WorkTimeDto> workTimes;

    @Builder
    private EmployeeAttendanceDto(String name, String phoneNum, List<WorkTimeDto> workTimes) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.workTimes = workTimes;
    }

    public static EmployeeAttendanceDto from(EmployeeAttendance employeeAttendance) {
        return EmployeeAttendanceDto.builder()
                .name(employeeAttendance.getStaffName())
                .phoneNum(employeeAttendance.getPhoneNumber())
                .workTimes(employeeAttendance.getRegisteredWorkingTime())
                .build();
    }

}
