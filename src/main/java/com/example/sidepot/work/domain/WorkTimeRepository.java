package com.example.sidepot.work.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkTimeRepository extends JpaRepository<WorkTime, Long> {
    //TODO JPA -> JPQL OR DSL 로 통일
    List<WorkTime> findAllByStaff_MemberId(Long staffId);
}
