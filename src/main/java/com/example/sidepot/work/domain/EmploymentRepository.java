package com.example.sidepot.work.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmploymentRepository extends JpaRepository<Employment, Long> {

    List<Employment> findAllByStore_StoreId(Long storeId);
    Optional<Employment> findByStore_StoreIdAndStaff_AuthId(Long storeId, Long staffId);
}
