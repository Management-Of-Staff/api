package com.example.sidepot.work.dto;

import com.example.sidepot.member.domain.Rank;
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
