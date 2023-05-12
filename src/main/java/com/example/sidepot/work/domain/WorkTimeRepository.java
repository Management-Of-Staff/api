package com.example.sidepot.work.domain;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkTimeRepository extends JpaRepository<WorkTime, Long> {

    @EntityGraph(attributePaths = {"store","staff"})
    List<WorkTime> findAllByWorkTimeId(Long workTimeId);
}
