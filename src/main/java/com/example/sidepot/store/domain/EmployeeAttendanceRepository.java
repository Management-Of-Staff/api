package com.example.sidepot.store.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeAttendanceRepository extends JpaRepository<EmployeeAttendance, Long> {

    List<EmployeeAttendance> findByStoreAndAndCheckOutTimeIsNull(Store store);
}
