package com.example.sidepot.command.work.repository;


import com.example.sidepot.command.work.domain.CoverNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoverNoticeRepository extends JpaRepository<CoverNotice, Long> {
    List<CoverNotice> findAllByCoverManagerId(Long coverManagerId);
    Optional<CoverNotice> findByCoverManagerId(Long coverManagerId);


}
