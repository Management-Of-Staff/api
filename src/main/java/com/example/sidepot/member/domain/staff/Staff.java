package com.example.sidepot.member.domain.staff;

import com.example.sidepot.member.domain.BaseEntity;
import com.example.sidepot.member.domain.Role;
import com.example.sidepot.member.domain.Employment;
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
public class Staff extends BaseEntity {

    @OneToMany(mappedBy = "staff")
    private List<Employment> store = new ArrayList<>();

    public Staff(String name, String phone, String password, Role role) {
        super(name, phone, password, role);
    }

    public static Staff of(String name, String phone, String password, Role role){ return new Staff(name, phone, password, role);}


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<Role> authorities = new ArrayList<>();
        authorities.add(this.getRole());
        return authorities;
    }

    @Override
    public String getUsername() {
        return super.getPhone();
    }

    public String getPassword(){
        return super.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
