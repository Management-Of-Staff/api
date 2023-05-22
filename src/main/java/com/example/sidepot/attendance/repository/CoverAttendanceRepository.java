package com.example.sidepot.attendance.repository;

import com.example.sidepot.attendance.domain.CoverAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoverAttendanceRepository extends JpaRepository<CoverAttendance, Long> {
}
