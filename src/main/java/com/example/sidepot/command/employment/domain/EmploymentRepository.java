package com.example.sidepot.command.employment.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmploymentRepository extends JpaRepository<Employment, Long> {
    List<Employment> findAllByStaff_MemberId(Long staffId);

    List<Employment> findAllByStore_StoreId(Long storeId);
    boolean existsByStaff_MemberIdAndStore_StoreId(Long storeId, Long staffId);

}
