package com.example.sidepot.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    boolean existsByPhone(String phone);

    Owner findByName(String name);

    Optional<Owner> findByPhone(String phone);

}
