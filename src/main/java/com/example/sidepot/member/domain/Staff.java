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
    public Staff updateMember(MemberDto.MemberUpdateDto memberUpdateDto) {
        super.updateMemberInfo(memberUpdateDto);
        return this;
    }
}
