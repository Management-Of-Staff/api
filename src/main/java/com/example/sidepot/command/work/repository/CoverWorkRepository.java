package com.example.sidepot.command.work.repository;

import com.example.sidepot.command.work.domain.CoverWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CoverWorkRepository extends JpaRepository<CoverWork, Long> {

    /**
     * 직원관련 모든 대타 조회
     * use) 근무 겹침 체크
     */
    List<CoverWork> findAllByAcceptedStaff_Id(Long staffId);


}
