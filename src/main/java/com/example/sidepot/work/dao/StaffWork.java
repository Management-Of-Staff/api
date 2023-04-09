package com.example.sidepot.work.dao;

import com.example.sidepot.attendance.domain.AttendanceStatus;
import lombok.Getter;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

@Getter
public class StaffWork implements Serializable {
    private Long employmentId;
    private String staffName;
    private String profileImage;
    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;
    private AttendanceStatus attendanceStatus;

    public StaffWork(Long employmentId, String staffName, String profileImage, DayOfWeek day,
                     LocalTime startTime, LocalTime endTime, AttendanceStatus attendanceStatus) {
        this.employmentId = employmentId;
        this.staffName = staffName;
        this.profileImage = profileImage;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.attendanceStatus = attendanceStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StaffWork that = (StaffWork) o;
        return employmentId.equals(that.employmentId)
                && startTime.equals(that.startTime)
                && endTime.equals(that.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employmentId, startTime, endTime);
    }
}
