package com.example.sidepot.employment.domain;

import com.example.sidepot.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface EmploymentRepository extends JpaRepository<Employment, Long> {
    List<Employment> findAllByStaff_MemberId(Long staffId);

    List<Employment> findAllByStore_StoreId(Long storeId);
    boolean existsByStaff_MemberIdAndStore_StoreId(Long storeId, Long staffId);

}
