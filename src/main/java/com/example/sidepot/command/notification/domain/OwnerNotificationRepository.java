package com.example.sidepot.command.notification.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerNotificationRepository extends JpaRepository<ONotification, Long> {
}