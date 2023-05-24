package com.example.sidepot.work.event;

import com.example.sidepot.notification.work.domain.CoverManagerId;
import com.example.sidepot.notification.work.domain.Receiver;
import com.example.sidepot.notification.work.domain.Sender;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CoverWorkAcceptedEvent {

   private CoverManagerId coverNoticeId;
   private Sender sender;
   private Receiver receiver;

    public CoverWorkAcceptedEvent(CoverManagerId coverNoticeId, Sender sender, Receiver receiver) {
        this.coverNoticeId = coverNoticeId;
        this.sender = sender;
        this.receiver = receiver;
    }
}
