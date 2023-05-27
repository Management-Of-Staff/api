package com.example.sidepot.command.attendance.dto;

import com.example.sidepot.command.attendance.domain.*;
import com.example.sidepot.command.work.domain.StoreInfo;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class AttendanceTodayResDto {
    List<AttendanceResDto> attendanceList;
    List<CoverAttendanceResDto> coverAttendanceList;

    public AttendanceTodayResDto(List<Attendance> attendances, List<CoverAttendance> coverAttendanceList) {
        this.attendanceList = attendances.stream().map(AttendanceResDto::new).collect(Collectors.toList());
        this.coverAttendanceList = coverAttendanceList.stream().map(CoverAttendanceResDto::new).collect(Collectors.toList());
    }

    @Getter
    public static class AttendanceResDto {
        private Long attendanceId;
        private StoreInfo storeInfo;
        private WorkerId workerId;
        private WorkDateTime workDateTime;
        private AttendanceStatus attendanceStatus;

        public AttendanceResDto(Attendance attendance) {
            this.attendanceId = attendance.getId();
            this.storeInfo = attendance.getStoreInfo();
            this.workerId = attendance.getWorkerId();
            this.workDateTime = attendance.getWorkDateTime();
            this.attendanceStatus = attendance.getAttendanceStatus();
        }
    }

    @Getter
    public static class CoverAttendanceResDto{
        private Long coverAttendanceId;
        private StoreInfo storeInfo;
        private WorkerId workerId;
        private WorkDateTime workDateTime;
        private AttendanceStatus attendanceStatus;

        public CoverAttendanceResDto(CoverAttendance coverAttendance) {
            this.coverAttendanceId = coverAttendance.getId();
            this.storeInfo = coverAttendance.getStoreInfo();
            this.workerId = coverAttendance.getWorkerId();
            this.workDateTime = coverAttendance.getWorkDateTime();
            this.attendanceStatus = coverAttendance.getAttendanceStatus();
        }
    }
}