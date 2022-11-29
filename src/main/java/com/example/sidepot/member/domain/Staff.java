package com.example.sidepot.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "staff")
public class Staff {

    @Id @Column(name = "staff_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long staffId;

    @Column(name = "staff_name")
    private String StaffName;

    @Column(name = "staff_phone")
    private String StaffPhone;

    @Column(name = "staff_password")
    private String StaffPassword;

    @OneToMany(mappedBy = "staff")
    private List<Store> store = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;
}
