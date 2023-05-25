package com.example.sidepot.command.work.repository.query;

import com.example.sidepot.command.work.domain.CoverDateTime;
import com.example.sidepot.command.work.domain.CoverManager;
import com.example.sidepot.command.work.domain.CoverManagerStatus;
import com.example.sidepot.command.work.domain.CoverWork;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Getter
public class CoverWorkResDto {

    @Getter
    public static class CoverWorkByNoticeResDto{
        private Long coverWorkId;
        private CoverDateTime coverDateTime;

        public CoverWorkByNoticeResDto(Long coverWorkId, CoverDateTime coverDateTime) {
            this.coverWorkId = coverWorkId;
            this.coverDateTime = coverDateTime;
        }
    }

    @Getter
    public static class RequestedCoverWorkResDto{
        private Long coverManagerId;
        private CoverManagerStatus coverManagerStatus;
        private CoverDetail thumbNailCoverWork;
        private List<CoverDetail> coverDetailList;

        public RequestedCoverWorkResDto(CoverManager coverManager, List<CoverWork> coverWorkList) {
            this.coverManagerId = coverManager.getId();
            this.coverManagerStatus = coverManager.getCoverManagerStatus();
            this.thumbNailCoverWork = coverWorkList.stream()
                    .min(Comparator.comparing(coverDetail -> coverDetail.getCoverDateTime().getCoverDate()))
                    .map(cw -> new CoverDetail(cw.getCoverWorkId(), cw.getCoverDateTime()))
                    .get();
            this.coverDetailList = coverWorkList.stream()
                            .map(cw -> new CoverDetail(cw.getCoverWorkId(), cw.getCoverDateTime()))
                            .collect(Collectors.toList());
        }
    }

    @Getter
    public static class AcceptedCoverWorkResDto{

    }

    @Getter
    public static class CoverDetail{
        private Long coverWorkId;
        private CoverDateTime coverDateTime;

        public CoverDetail(Long coverWorkId, CoverDateTime coverDateTime) {
            this.coverWorkId = coverWorkId;
            this.coverDateTime = coverDateTime;
        }
    }
}
