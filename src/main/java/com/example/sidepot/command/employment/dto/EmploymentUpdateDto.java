package com.example.sidepot.command.employment.dto;

import com.example.sidepot.command.member.domain.Rank;
import lombok.Getter;


public class EmploymentUpdateDto {
    @Getter
    public static class UpdateRankAndWageRequest {
        private Long hourlyWage;
        private Rank rank;
    }
}
