package com.example.sidepot.security.domain;

import com.example.sidepot.member.domain.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@NoArgsConstructor
@DiscriminatorColumn(name = "d_type")
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public abstract class Auth implements UserDetails {

    @Id @Column(name = "auth_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authId;
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone", nullable = false )
    private String phone;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Auth(String name, String phone, String password, Role role) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<Role> authorities = new ArrayList<>();
        authorities.add(this.getRole());
        return authorities;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
