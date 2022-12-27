package com.example.sidepot.member.domain;


import com.example.sidepot.security.domain.Auth;
//import com.example.sidepot.member.domain.Store;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter // 테스트
@Getter
@NoArgsConstructor
@Entity
@DiscriminatorValue("owner")
@Table(name = "owner")
public class Owner extends Auth {


//    @OneToMany(mappedBy = "owner")
//    private List<Store> storeList = new ArrayList<>();

    @Builder //테스트
    public Owner(String name, String phone, String password, Role role) {
        super(name,phone,password,role);
    }

    public static Owner of(String name, String phone, String password, Role role){
        return new Owner(name, phone, password, role);
    }


}
