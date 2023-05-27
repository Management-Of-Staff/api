package com.example.sidepot.command.notification.firebase;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@AllArgsConstructor
@Builder
public class FCMessage {
    private boolean validate_only;
    private Message message;
    @Builder
    @AllArgsConstructor
    @Getter
    public static class Message {
        private Notification notification;
        private String token;
    }

    @Getter
    @Builder
    public static class Notification{
        private String title;
        private String body;

    }
}
