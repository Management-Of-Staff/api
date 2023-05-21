package com.example.sidepot.work.event;

import com.example.sidepot.notification.work.domain.CoverManagerId;
import com.example.sidepot.notification.work.domain.ReceiverId;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CoverWorkAcceptedEvent {

   private CoverManagerId coverNoticeId;
   private ReceiverId receiverId;

    public CoverWorkAcceptedEvent(CoverManagerId coverNoticeId, ReceiverId receiverId) {
        this.coverNoticeId = coverNoticeId;
        this.receiverId = receiverId;
    }
}
