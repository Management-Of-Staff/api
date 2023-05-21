package com.example.sidepot.work.repository;

import com.example.sidepot.work.domain.CoverManager;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoverManagerRepository extends JpaRepository<CoverManager, Long> {

    @EntityGraph(attributePaths = {"coverWorkList"})
    Optional<CoverManager> findById(Long coverNoticeId);
}
