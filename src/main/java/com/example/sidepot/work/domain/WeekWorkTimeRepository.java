package com.example.sidepot.work.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeekWorkTimeRepository extends JpaRepository<WeekWorkTime, Long> {
}