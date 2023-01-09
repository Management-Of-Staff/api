package com.example.sidepot.member.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@Entity
@DiscriminatorValue("owner")
@Table(name = "owner")
public class Owner extends Auth {
    @Setter
    private String testCode;

    @Builder
    public Owner(Long id, String UUID, String email, String name, String phone, String password, Role role,
                 LocalDate birthDate, LocalDateTime createDate, LocalDateTime deleteDate, String testCode) {
        super(id, UUID, email, name, phone, password, role, birthDate, createDate, deleteDate);
        this.testCode = testCode;
    }

    public Owner(String name, String phone, String password, String uuid, Role role, LocalDateTime createDate) {
        super(name, phone, password, uuid, role, createDate);
    }

    public static Owner of(String name, String phone, String password, String uuid, Role role, LocalDateTime createDate){
        return new Owner(name, phone, password, uuid, role, createDate);
    }
}
