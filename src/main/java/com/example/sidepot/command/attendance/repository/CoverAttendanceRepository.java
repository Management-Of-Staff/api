package com.example.sidepot.command.attendance.repository;

import com.example.sidepot.command.attendance.domain.CoverAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoverAttendanceRepository extends JpaRepository<CoverAttendance, Long> {
}
