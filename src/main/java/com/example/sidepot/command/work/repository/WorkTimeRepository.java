package com.example.sidepot.command.work.repository;

import com.example.sidepot.command.member.domain.Staff;
import com.example.sidepot.command.work.domain.WorkTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface WorkTimeRepository extends JpaRepository<WorkTime, Long> {

    List<WorkTime> findAllByStaff(Staff staff);

    //schedule
    List<WorkTime> findAllByDayOfWeek(DayOfWeek dayOfWeek);
}
