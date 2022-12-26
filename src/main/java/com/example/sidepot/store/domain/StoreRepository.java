package com.example.sidepot.store.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

}
