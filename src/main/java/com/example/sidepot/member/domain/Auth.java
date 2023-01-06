package com.example.sidepot.member.domain;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private Long UUID;

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
    private Date birthDate;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "delete_date")
    private LocalDate deleteDate;

    public Auth(Claims claims){
        this.id = Long.valueOf(claims.get("userId").toString());
        this.name = claims.get("name").toString();
        this.phone = claims.getSubject();
    }

    public Auth(String name, String phone, String password, Role role){
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }

    public Auth(Long id, Long UUID, String email, String name, String phone, String password, Role role, Date birthDate, LocalDate createDate, LocalDate deleteDate) {
        this.id = id;
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
