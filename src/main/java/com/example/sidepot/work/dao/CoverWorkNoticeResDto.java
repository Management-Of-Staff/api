package com.example.sidepot.work.dao;

import com.example.sidepot.work.domain.CoverNoticeType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

public class CoverWorkNoticeResDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CoverNoticeResDto{
        private Long coverWorkNoticeId;
        private CoverNoticeType coverNoticeType;
        private LocalDateTime create_dt;
        private boolean readStatus;
        private boolean accepted;
        private Long storeId;
        private String branchName;
        private Long requestedStaffId;
        private String requestedStaffName;
        private Set<CoverWorkDto> coverWorkList;
    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CoverWorkDto{
        private Long coverWorkId;
        private LocalDate coverDate;
        private LocalTime startTime;
        private LocalTime endTime;
    }
}
