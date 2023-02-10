package com.example.sidepot.store.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "notice_manager")
@NoArgsConstructor
@Entity
public class NoticeImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_manager_id")
    private Long noticeWriterId;

    @Column(name = "image_path")
    private String imagePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    private Notice notice;

}
