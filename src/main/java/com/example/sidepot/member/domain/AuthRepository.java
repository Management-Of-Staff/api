package com.example.sidepot.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth, Long> {

    Optional<Auth> findByPhone(String phone);
    List<Auth> findAll();

    boolean existsByPhone(String phone);
    boolean existsByPhoneAndDeleteDateIsNotNull(String phone);

    @Modifying(clearAutomatically = true)
    @Query("update Auth m set m.birthDate = ?2, m.email = ?3 where m.id = ?1 ")
    Optional<Integer> updateMemberProfile(Long id, Date birthDate, String email);

    @Modifying(clearAutomatically = true)
    @Query("update Auth m set m.phone = ?2 where m.id = ?1")
    Optional<Integer> updateMemberPhone(Long id, String phone);

    @Modifying(clearAutomatically = true)
    @Query("update Auth m set m.password = ?2 where m.id = ?1")
    Optional<Integer> updateMemberPassword(Long id, String password);

    @Modifying(clearAutomatically = true)
    @Query("update Auth m set m.deleteDate = ?2 where m.id = ?1")
    Optional<Integer> updateMemberDeleteDate(Long id, LocalDate date);

}

