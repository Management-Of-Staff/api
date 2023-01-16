package com.example.sidepot.store.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
    List<Store> findAllByOwnerId(Long ownerId);
    Optional<Store> getByStoreId(Long storeId);
    void deleteByStoreId(Long storeId);

}
