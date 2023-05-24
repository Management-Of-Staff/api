package com.example.sidepot.work.repository;

import com.example.sidepot.work.domain.CoverWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CoverWorkRepository extends JpaRepository<CoverWork, Long> {

    List<CoverWork> findAllByAcceptedStaff_Id(Long staffId);
    List<CoverWork> findByWorkTime_WorkTimeIdAndCoverDateTime_CoverDateAfter(Long workTimeId, LocalDate now);
    Optional<CoverWork> findByWorkTime_WorkTimeIdAndCoverDateTime_CoverDate(Long workTimeId, LocalDate now);

}
