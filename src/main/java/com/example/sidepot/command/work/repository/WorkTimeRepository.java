package com.example.sidepot.command.work.repository;

import com.example.sidepot.command.member.domain.Staff;
import com.example.sidepot.command.work.domain.WorkTime;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface WorkTimeRepository extends JpaRepository<WorkTime, Long> {

    /**
     * 직원관련 모든 근무를 조회
     * use) 근무 겹침 체크
     * ref) 도메인 무결성이 꺠지는 코드, staff 참조 금지
     */
    List<WorkTime> findAllByStaff(Staff staff);

}
