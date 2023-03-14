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
@AttributeOverrides({
        @AttributeOverride(name = "memberId", column = @Column(name = "owner_id"))
})
@Entity
@Table(name = "owner")
public class Owner extends Member {
    @OneToMany(mappedBy = "owner")
    private List<Store> storeList = new ArrayList<>();

    private Owner(String name, String password, String phoneNum, Role role, LocalDateTime createDate) {
        super(name, password, phoneNum,  role, createDate);
    }

    public static Owner registerOwner(String name, String password, String phoneNum, LocalDateTime createDate){
        return new Owner(name, password, phoneNum, Role.OWNER, createDate);
    }
}
