package com.example.sidepot.attendance.dto;

import com.example.sidepot.attendance.domain.Attendance;
import com.example.sidepot.store.dto.WorkTimeDto;
import lombok.Getter;

import java.util.List;

@Getter
public class EmployeeAttendanceDto {
    private String name;
    private String phoneNum;
    List<WorkTimeDto> workTimes;

    private EmployeeAttendanceDto(String name, String phoneNum, List<WorkTimeDto> workTimes) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.workTimes = workTimes;
    }

    public static EmployeeAttendanceDto from(Attendance attendance) {
        return new EmployeeAttendanceDto(attendance.getStaffName(), attendance.getPhoneNumber(),
                attendance.getRegisteredWorkingTime());
    }

}
