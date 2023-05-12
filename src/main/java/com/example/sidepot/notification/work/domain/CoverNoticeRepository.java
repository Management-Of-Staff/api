package com.example.sidepot.notification.work.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoverNoticeRepository extends JpaRepository<CoverNotice, Long> {
}
