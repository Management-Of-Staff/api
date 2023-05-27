package com.example.sidepot.command.attendance.repository;

import com.example.sidepot.command.attendance.domain.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByWorkTimeIdAndWorkDateTime_DayOfWeek(Long workTimeId, DayOfWeek day);
}
