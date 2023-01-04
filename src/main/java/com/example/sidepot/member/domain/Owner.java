package com.example.sidepot.member.domain;


import com.example.sidepot.member.dto.MemberDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;


@Getter
@NoArgsConstructor
@Entity
@DiscriminatorValue("owner")
@Table(name = "owner")
public class Owner extends Auth {

    @Builder
    public Owner(String name, String phone, String password, Role role) {
        super(name, phone, password, role);
    }

    public static Owner of(String name, String phone, String password, Role role){
        return new Owner(name, phone, password, role);
    }

    @Override
    public Owner updateMember(MemberDto.MemberUpdateDto memberUpdateDto) {
        super.updateMemberInfo(memberUpdateDto);
        return this;
    }
}
