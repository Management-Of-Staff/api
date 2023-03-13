package com.example.sidepot.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByMemberPhoneNum(String phoneNum);
    boolean existsByMemberPhoneNum(String phone);
    boolean existsByMemberPhoneNumAndWithdrawalDateIsNotNull(String staffId);



}
