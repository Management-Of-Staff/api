package com.example.sidepot.work.domain;

import com.example.sidepot.member.domain.Staff;
import com.example.sidepot.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmploymentRepository extends JpaRepository<Employment, Long> {
    List<Employment> findAllByStore_StoreId(Long storeId);
    Optional<Employment> findByStore_StoreIdAndStaff_MemberId(Long storeId, Long staffId);
    boolean existsByStaff_MemberIdAndStore_StoreId(Long storeId, Long staffId);

}
