package com.example.sidepot.store.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleManagerRepository extends JpaRepository<ScheduleManager, Long> {
    ScheduleManager getScheduleManagerByScheduleManagerId(Long scheduleManagerId);
}
