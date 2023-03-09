package com.example.sidepot.member.dto;

import com.example.sidepot.member.domain.Rank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class ContractCreateDto {

    @Data
    @NoArgsConstructor
    public static class ContractCreateRequestDto{

        private LocalDate startWorkDate;
        private Rank rank;
        private int contractTerm;
        private int probation;
        private String simpleTask;
    }
}
