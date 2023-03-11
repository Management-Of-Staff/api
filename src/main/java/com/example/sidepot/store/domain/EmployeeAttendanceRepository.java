package com.example.sidepot.store.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface EmployeeAttendanceRepository extends JpaRepository<EmployeeAttendance, Long> {

    List<EmployeeAttendance> findByStoreAndCheckOutTimeIsNull(Store store);

    @Query("SELECT e FROM EmployeeAttendance e " +
            "WHERE e.attendanceStatus = 'ATTENDANCE' " +
            "AND e.store = :store " +
            "AND e.checkOutTime IS NULL " +
            "AND e.checkInTime >= :startDate")
    List<EmployeeAttendance> getTodayNormalAttendanceListByStore(@Param("store") Store store,
                                                                 @Param("startDate") LocalDateTime startDate);
}
