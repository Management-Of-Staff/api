package com.example.sidepot.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {

    Optional<Auth> findByPhone(String phone);
    List<Auth> findAll();

    boolean existsByPhone(String phone);
    boolean existsByPhoneAndDeleteDateIsNotNull(String phone);

}

