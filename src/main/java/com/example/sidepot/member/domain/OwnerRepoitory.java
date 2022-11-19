package com.example.sidepot.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;


public interface OwnerRepoitory extends JpaRepository<Owner, Long> {

    boolean existsByPhone(String phone);

}
