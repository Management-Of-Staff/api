package com.example.sidepot.member.domain;


import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@NoArgsConstructor
@Repository
public class Authority {

    @Id @Column(name = "authority_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;

    Role role;
}
