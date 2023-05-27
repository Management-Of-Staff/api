package com.example.sidepot.command.work.repository;

import com.example.sidepot.command.work.domain.WorkManager;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkManagerRepository extends JpaRepository<WorkManager, Long> {

    /**
     *  DAO 로 전환 예정(낮음)
     */
    List<WorkManager> findAllByStoreInfo_StoreId(Long StoreId);

    /**
     * 자식 엔티티는 벨류 객체로 취급하여 무결성 유지하고 JPA 매핑기술만 기술만 사용
     * 자식 객체가 관리할 영속성 없으므로 페치조인을 허용
     */
    @EntityGraph(attributePaths = "workTimeList")
    Optional<WorkManager> findById(Long id);

}
