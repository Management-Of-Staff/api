package com.example.sidepot.member.domain;

import com.example.sidepot.member.dto.MemberDto.MemberUpdateDto;
import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@NoArgsConstructor
@DiscriminatorColumn(name = "d_type")
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public class Auth{

    @Id @Column(name = "auth_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authId;

    @Column(name = "uuid")
    private String UUID;

    @Column(name = "email")
    private String Email;
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "delete_date")
    private LocalDate deleteDate;


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

    public Auth updateMember(MemberUpdateDto memberUpdateDto){
        this.name = memberUpdateDto.getName();
        this.password = memberUpdateDto.getPassword();
        return this;
    }
}
