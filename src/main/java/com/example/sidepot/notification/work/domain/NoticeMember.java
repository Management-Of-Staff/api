package com.example.sidepot.notification.work.domain;

import com.example.sidepot.member.domain.Staff;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "notice_member")
@Entity
public class NoticeMember {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne //영속성 미결
    @JoinColumn(name = "cover_notice_id")
    private CoverNotice coverNotice;

    @ManyToOne //영속성 미결
    @JoinColumn(name = "receiver_id")
    private Staff receiver;

    @Column(name = "is_read")
    private boolean isRead;

    public NoticeMember(CoverNotice coverNotice, Staff receiver, boolean isRead) {
        this.coverNotice = coverNotice;
        this.receiver = receiver;
        this.isRead = isRead;
    }

    public void setCOverNotice(CoverNotice coverNotice){
        this.coverNotice = coverNotice;
    }
}
