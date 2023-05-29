package com.example.sidepot.command.work.domain;

import com.example.sidepot.command.member.domain.Staff;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Sender {

    private Long senderId;
    private String senderName;

    public Sender(Long senderId, String senderName) {
        this.senderId = senderId;
        this.senderName = senderName;
    }

    public Sender(RequestedStaff requestedStaff){
        this.senderId = requestedStaff.getId();
        this.senderName = requestedStaff.getName();
    }

    public Sender(AcceptedStaff acceptedStaff){
        this.senderId = acceptedStaff.getId();
        this.senderName = acceptedStaff.getName();
    }

    public Sender(Staff staff){
        this.senderId = staff.getMemberId();
        this.senderName = staff.getMemberName();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sender sender1 = (Sender) o;
        return Objects.equals(senderId, sender1.senderId) && Objects.equals(senderName, sender1.senderName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(senderId, senderName);
    }
}
