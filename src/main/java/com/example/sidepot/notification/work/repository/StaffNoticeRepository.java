package com.example.sidepot.notification.work.repository;


import com.example.sidepot.notification.work.domain.CoverManagerId;
import com.example.sidepot.notification.work.domain.StaffNotice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffNoticeRepository extends JpaRepository<StaffNotice, Long> {
    List<StaffNotice> findAllByCoverManagerId(CoverManagerId coverManagerId);
    Optional<StaffNotice> findByCoverManagerId(CoverManagerId coverManagerId);


}
