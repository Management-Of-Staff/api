package com.example.sidepot.attendance.domain;

import com.example.sidepot.store.domain.Store;
import com.example.sidepot.work.domain.Employment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    List<Attendance> findByStoreAndCheckOutTimeIsNull(Store store);

    @Query("SELECT a FROM Attendance a " +
            "WHERE a.attendanceStatus = 'CHECK_IN' " +
            "AND a.store = :store")
    List<Attendance> getCheckInListByStore(@Param("store") Store store);

    @Query("SELECT a FROM Attendance a where a.attendanceStatus = :status and a.store = :store and a.employment = :employment")
    Optional<Attendance> findAttendanceByCondition(@Param("status") AttendanceStatus status, @Param("store") Store store, @Param("employment") Employment employment);
}
