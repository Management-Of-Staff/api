package com.example.sidepot.member.domain;

import io.jsonwebtoken.Claims;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@DiscriminatorColumn(name = "d_type")
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public class Auth{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_id")
    private Long authId;

    @Column(name = "uuid")
    private String UUID;

    @Column(name = "email")
    private String email;
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
    private LocalDate birthDate;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "delete_date")
    private LocalDateTime deleteDate;

    public Auth(Claims claims){
        this.authId = Long.valueOf(claims.get("userId").toString());
        this.name = claims.get("name").toString();
        this.phone = claims.getSubject();
    }

    public Auth(String name, String phone, String password, String uuid, Role role, LocalDateTime createDate){
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.UUID = uuid;
        this.role = role;
        this.createDate = createDate;
    }

    public Auth(Long authId, String UUID, String email, String name, String phone, String password,
                Role role, LocalDate birthDate, LocalDateTime createDate, LocalDateTime deleteDate) {
        this.authId = authId;
        this.UUID = UUID;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.role = role;
        this.birthDate = birthDate;
        this.createDate = createDate;
        this.deleteDate = deleteDate;
    }
}
