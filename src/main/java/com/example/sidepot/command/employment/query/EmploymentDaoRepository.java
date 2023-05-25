package com.example.sidepot.command.employment.query;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@RequiredArgsConstructor
@Repository
public class EmploymentDaoRepository {
    private final JPAQueryFactory jpaQueryFactory;


    public void 고용직원목록보기(Long storeId, LocalDate today){

    }
}
