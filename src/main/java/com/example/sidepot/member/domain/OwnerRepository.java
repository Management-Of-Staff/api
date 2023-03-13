package com.example.sidepot.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Owner findByMemberId(Long ownerId);
    Optional<Owner> findByMemberPhoneNum(String phoneNum);
    boolean existsByMemberPhoneNum(String phone);
    boolean existsByMemberPhoneNumAndWithdrawalDateIsNotNull(String staffId);
    List<Owner> findAll();

}
