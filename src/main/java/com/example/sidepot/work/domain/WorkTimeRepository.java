package com.example.sidepot.work.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkTimeRepository extends JpaRepository<WorkTime, Long> {
    Optional<WorkTime> findById(Long weekWorkTimeId);
}
