package com.example.sidepot.security.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository<T extends Auth> extends JpaRepository<T, Long> {

    Optional<T> findByPhone(String phone);

}
