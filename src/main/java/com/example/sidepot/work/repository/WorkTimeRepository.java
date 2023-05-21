package com.example.sidepot.work.repository;

import com.example.sidepot.member.domain.Staff;
import com.example.sidepot.work.domain.WorkTime;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface WorkTimeRepository extends JpaRepository<WorkTime, Long> {

    List<WorkTime> findAllByStaff(Staff staff);
}
