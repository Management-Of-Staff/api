package com.example.sidepot.store.domain;

import com.example.sidepot.member.domain.Owner;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findAllByOwner(Owner owner);
    Optional<Store> getByStoreId(Long storeId);
    void deleteByStoreId(Long storeId);
    Optional<Store> findByOwnerAndStoreId(Owner owner, Long StoreId);

    @EntityGraph(attributePaths = {"employment"})
    List<Store> findAllByStoreId(Long storeId);
}

