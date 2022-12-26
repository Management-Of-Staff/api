package com.example.sidepot.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BaseEntityRepository<T extends BaseEntity> extends JpaRepository<BaseEntity, Long> {
    Optional<BaseEntity> findByPhone(String phone);
}
