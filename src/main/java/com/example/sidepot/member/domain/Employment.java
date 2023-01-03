package com.example.sidepot.member.domain;

import com.example.sidepot.store.domain.Store;

import javax.persistence.*;

@Entity
public class Employment {

    @Id @GeneratedValue
    @Column(name = "employment_id")
    private Long workId;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;
}
