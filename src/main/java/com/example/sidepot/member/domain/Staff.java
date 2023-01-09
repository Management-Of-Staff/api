package com.example.sidepot.member.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@DiscriminatorValue("staff")
@Entity
@Table(name = "staff")
public class Staff extends Auth {

    @Builder
    public Staff(String name, String phone, String password, String uuid, Role role, LocalDateTime createDate) {
        super(name, phone, password, uuid, role, createDate);
    }

    public static Staff of(String name, String phone, String password, String uuid, Role role, LocalDateTime createDate){
        return new Staff(name, phone, password, uuid, role, createDate);}

}
