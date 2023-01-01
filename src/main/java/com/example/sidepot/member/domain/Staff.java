package com.example.sidepot.member.domain;

import com.example.sidepot.member.dto.MemberDto;
//import com.example.sidepot.member.domain.Employment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import javax.persistence.*;

@Getter
@NoArgsConstructor
@DiscriminatorValue("staff")
@Entity
@Table(name = "staff")
public class Staff extends Auth {

    @Builder
    public Staff(String name, String phone, String password, Role role) {
        super(name, phone, password, role);
    }

    public static Staff of(String name, String phone, String password, Role role){ return new Staff(name, phone, password, role);}

    @Override
    public Staff update(MemberDto.MemberUpdateDto memberUpdateDto) {
        super.update(memberUpdateDto);
        return this;
    }
}
