package com.example.sidepot.command.work.presentation;


import com.example.sidepot.command.work.domain.CoverManagerId;
import com.example.sidepot.command.work.domain.StaffCoverNoticeBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffCoverNoticeRepository extends JpaRepository<StaffCoverNoticeBox, Long> {
    List<StaffCoverNoticeBox> findAllByCoverManagerId(CoverManagerId coverManagerId);
    Optional<StaffCoverNoticeBox> findByCoverManagerId(CoverManagerId coverManagerId);


}
