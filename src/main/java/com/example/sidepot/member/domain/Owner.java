package com.example.sidepot.member.domain;


import com.example.sidepot.member.dto.MemberRegisterDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Date;


@Getter
@NoArgsConstructor
@Entity
@DiscriminatorValue("owner")
@Table(name = "owner")
public class Owner extends Auth {

    @Setter
    private String testCode;
    @Builder
    public Owner(Long id, Long UUID, String email, String name, String phone, String password, Role role,
                 Date birthDate, LocalDate createDate, LocalDate deleteDate, String testCode) {
        super(id, UUID, email, name, phone, password, role, birthDate, createDate, deleteDate);
        this.testCode = testCode;
    }

    @Builder
    public Owner(String name, String phone, String password, Role role) {
        super(name, phone, password, role);
    }

    public static Owner of(String name, String phone, String password, Role role){
        return new Owner(name, phone, password, role);
    }
}
