package com.example.sidepot.command.notification.work.dto;

import com.example.sidepot.command.notification.work.domain.NoticeType;
import com.example.sidepot.command.notification.work.domain.StaffNotice;
import lombok.Getter;

import java.time.LocalDateTime;

public class CoverNoticeResDto {
    @Getter
    public static class CoverNoticeBoxResDto{
        private boolean isRead;
        private Long noticeId;
        private Long senderId;
        private String senderName;
        private String message;
        private LocalDateTime create_dt;
        private String detailsUrl;

        public CoverNoticeBoxResDto(boolean isRead, Long noticeId, Long senderId, String senderName,
                                    NoticeType noticeType, LocalDateTime create_dt, String detailsUrl) {
            this.isRead = isRead;
            this.noticeId = noticeId;
            this.senderId = senderId;
            this.senderName = senderName;
            this.message = noticeType.getMessage();
            this.create_dt = create_dt;
            this.detailsUrl = detailsUrl;
        }
    }

    @Getter
    public static class CoverNoticeThumbnailResDto {
        private Long notReadNum;
        private ThumbnailNoticeBox thumbnailNoticeBox;

        public CoverNoticeThumbnailResDto(Long notReadNum, StaffNotice staffNotice) {
            this.notReadNum = notReadNum != null ? notReadNum : 0L; // 기본값으로 0L 할당
            this.thumbnailNoticeBox = new ThumbnailNoticeBox(staffNotice);
        }
    }

    @Getter
    public static class ThumbnailNoticeBox{
        private String senderName;
        private String message;
        private LocalDateTime create_dt;

        public ThumbnailNoticeBox(StaffNotice staffNotice) {
            this.senderName = staffNotice != null ? staffNotice.getSender().getSenderName() : ""; // 기본값으로 빈 문자열 할당
            this.message = staffNotice != null ? staffNotice.getNoticeType().getMessage() : "최신 알림이 없네요?"; // 기본값으로 빈 문자열 할당
            this.create_dt = staffNotice != null ? staffNotice.getCreateDt() : LocalDateTime.now();
        }
    }
}