package com.example.sidepot.member.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@DiscriminatorColumn(name = "d_type")
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public abstract class BaseEntity implements UserDetails {

    @Id @Column(name = "base_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long baseId;
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone", nullable = false )
    private String phone;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    // private DateTime created;
    // private DateTime updated;


    public BaseEntity(String name, String phone, String password, Role role) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }

}
