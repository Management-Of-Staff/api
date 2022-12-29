package com.example.sidepot.security.domain;

import com.example.sidepot.member.domain.Role;
import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@NoArgsConstructor
@DiscriminatorColumn(name = "d_type")
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public class Auth{

    @Id @Column(name = "auth_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authId;
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Auth(Claims claims){
        this.authId = Long.valueOf(claims.get("userId").toString());
        this.name = claims.get("name").toString();
        this.phone = claims.getSubject();
    }

    public Auth(String name, String phone, String password, Role role) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }
}
