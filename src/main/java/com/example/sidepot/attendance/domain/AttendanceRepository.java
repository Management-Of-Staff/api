package com.example.sidepot.attendance.domain;

import com.example.sidepot.store.domain.Store;
import com.example.sidepot.work.domain.Employment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByStoreAndCheckOutTimeIsNull(Store store);

    @Query("SELECT e FROM Attendance e " +
            "WHERE e.attendanceStatus = 'CHECK_IN' " +
            "AND e.store = :store")
    List<Attendance> getCheckInListByStore(@Param("store") Store store);

    Optional<Attendance> findByAttendanceStatusAndEmployment(AttendanceStatus status, Employment employment);
}
