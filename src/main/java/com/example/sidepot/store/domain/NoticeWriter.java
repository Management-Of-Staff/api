package com.example.sidepot.store.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "notice_writer")
@NoArgsConstructor
@Entity
public class NoticeWriter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_wirter_id")
    private Long noticeWriterId;

    @Column(name = "role")
    private String role;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "noticeWriter")
    @Column(name = "notice_id")
    private List<Notice> noticeList = new ArrayList<>();


}
