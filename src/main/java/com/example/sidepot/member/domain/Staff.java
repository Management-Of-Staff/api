package com.example.sidepot.member.domain;

import com.example.sidepot.security.domain.Auth;
//import com.example.sidepot.member.domain.Employment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@DiscriminatorValue("staff")
@Table(name = "staff")
public class Staff extends Auth {

    /*
    @OneToMany(mappedBy = "staff")
    private List<Employment> store = new ArrayList<>();
     */

    public Staff(String name, String phone, String password, Role role) {
        super(name, phone, password, role);
    }

    public static Staff of(String name, String phone, String password, Role role){ return new Staff(name, phone, password, role);}

}
