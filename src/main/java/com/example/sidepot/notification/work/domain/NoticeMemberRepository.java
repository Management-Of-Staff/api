package com.example.sidepot.notification.work.domain;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeMemberRepository extends JpaRepository<NoticeMember, Long> {

    //TODO DSL로 리팩토링 옵션 뒤지게 많이들감
    @EntityGraph(attributePaths = {"coverNotice", "receiver"})
    List<NoticeMember> findAllByReceiver_MemberId(Long staffId);
}
