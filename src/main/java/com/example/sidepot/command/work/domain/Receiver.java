package com.example.sidepot.command.work.domain;

import com.example.sidepot.command.member.domain.Owner;
import com.example.sidepot.command.member.domain.Staff;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor
@Embeddable
public class Receiver {

    private Long receiverId;
    private String receiverName;
    private String receiverToken;

    public Receiver(Long receiverId, String receiverName, String receiverToken) {
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.receiverToken = receiverToken;
    }
    public Receiver(Staff staff) {
        this.receiverId = staff.getMemberId();
        this.receiverName = staff.getMemberName();
        this.receiverToken = staff.getUuid();
    }

    public Receiver(Owner owner){
        this.receiverId = owner.getMemberId();
        this.receiverName = owner.getMemberName();
        this.receiverToken = owner.getUuid();
    }

    public Receiver(RequestedStaff requestedStaff){
        this.receiverId = requestedStaff.getId();
        this.receiverName = requestedStaff.getName();
        this.receiverToken = requestedStaff.getToken();
    }

    public Receiver(AcceptedStaff acceptedStaff){
        this.receiverId = acceptedStaff.getId();
        this.receiverName = acceptedStaff.getName();
        this.receiverToken = acceptedStaff.getToken();
    }
}
