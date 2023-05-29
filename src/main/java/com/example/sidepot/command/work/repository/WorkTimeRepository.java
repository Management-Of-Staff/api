package com.example.sidepot.command.work.repository;


import com.example.sidepot.command.work.domain.WorkTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WorkTimeRepository extends JpaRepository<WorkTime, Long> {

}
