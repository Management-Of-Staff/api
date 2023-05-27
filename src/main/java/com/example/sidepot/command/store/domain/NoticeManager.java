package com.example.sidepot.command.store.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "notice_manager")
@NoArgsConstructor
@Entity
public class NoticeManager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_manager_id")
    private Long noticeWriterId;

    @Column(name = "notice_manager_name")
    private String name;

    @OneToMany(mappedBy = "noticeManager")
    private List<NoticeNoticeManager> noticeNoticeManagers = new ArrayList<>();

}
