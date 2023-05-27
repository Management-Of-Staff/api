package com.example.sidepot.command.employment.repository;

import com.example.sidepot.command.employment.domain.Employment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmploymentRepository extends JpaRepository<Employment, Long> {

    @EntityGraph(attributePaths = {"workTimeList", "staff"})
    List<Employment> findAllByStore_StoreId(Long storeId);

    // #DAO
    boolean existsByStaff_MemberIdAndStore_StoreId(Long storeId, Long staffId);

}
