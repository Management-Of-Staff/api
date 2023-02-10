package com.example.sidepot.store.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;

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

}
