package com.example.sidepot.command.attendance.repository;

import com.example.sidepot.command.attendance.domain.CoverAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CoverAttendanceRepository extends JpaRepository<CoverAttendance, Long> {

    Optional<CoverAttendance> findByCoverWorkIdAndWorkDateTime_WorkDate(Long coverWorkId, LocalDate workDate);
}
