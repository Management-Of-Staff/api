package com.example.sidepot.member.domain.owner;


import com.example.sidepot.member.domain.BaseEntity;
import com.example.sidepot.member.domain.Role;
import com.example.sidepot.member.domain.Store;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Setter // 테스트
@Getter
@NoArgsConstructor
@Entity
@DiscriminatorValue("owner")
@Table(name = "owner")
public class Owner extends BaseEntity {



    @OneToMany(mappedBy = "owner")
    private List<Store> storeList = new ArrayList<>();

    @Builder //테스트
    public Owner(String name, String phone, String password, Role role) {
        super(name,phone,password,role);
    }

    public static Owner of(String name, String phone, String password, Role role){
        return new Owner(name, phone, password, role);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<Role> authorities = new ArrayList<>();
        authorities.add(super.getRole());
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.getPhone();
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
