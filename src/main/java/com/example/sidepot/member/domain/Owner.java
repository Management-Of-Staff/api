package com.example.sidepot.member.domain;


import com.example.sidepot.store.domain.Store;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
@Entity
@DiscriminatorValue("owner")
@Table(name = "owner")
public class Owner extends Auth {
    @OneToMany(mappedBy = "owner")
    private List<Store> storeList = new ArrayList<>();

    @Builder
    public Owner(Long id, String UUID, String email, String name, String phone, String password, Role role,
                 LocalDate birthDate, LocalDateTime createDate, LocalDateTime deleteDate, String testCode) {
        super(id, UUID, email, name, phone, password, role, birthDate, createDate, deleteDate);
    }

    public Owner(String name, String phone, String password, String uuid, Role role, LocalDateTime createDate) {
        super(name, phone, password, uuid, role, createDate);
    }

    public static Owner of(String name, String phone, String password, String uuid, Role role, LocalDateTime createDate){
        return new Owner(name, phone, password, uuid, role, createDate);
    }
}
