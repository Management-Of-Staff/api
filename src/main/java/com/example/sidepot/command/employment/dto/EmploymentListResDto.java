package com.example.sidepot.command.employment.dto;

import com.example.sidepot.command.attendance.domain.AttendanceStatus;
import com.example.sidepot.command.work.domain.WorkManager;
import lombok.Getter;

import java.time.DayOfWeek;
import java.util.List;

@Getter
public class EmploymentListResDto {
    private Long staffId;
    private String staffName;
    private String profileImagePath;
    private List<DayOfWeek> dayOfWeekList;
    private AttendanceStatus attendanceStatus;

    public EmploymentListResDto(WorkManager workManager) {
        this.staffId = workManager.getWorkerId().getWorkerId();
        this.staffName = workManager.getWorkerId().getWorkerName();
        this.profileImagePath = null;
    }

    public EmploymentListResDto setAttendanceStatus(AttendanceStatus attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
        return this;
    }

    public EmploymentListResDto setDayOfWeekList(List<DayOfWeek> days) {
        this.dayOfWeekList = days;
        return this;
    }
}
