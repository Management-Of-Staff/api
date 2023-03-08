package com.example.sidepot.member.dto;

import com.example.sidepot.work.domain.Rank;
import lombok.Getter;


public class EmploymentUpdateDto {

    @Getter
    public static class UpdateRankAndWageRequest {

        private Long staffId;
        private Long storeId;
        private Long hourlyWage;
        private Rank rank;
    }
}
