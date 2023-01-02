package com.example.sidepot.member.domain;


import com.example.sidepot.member.dto.MemberDto;
//import com.example.sidepot.member.domain.Store;
import lombok.*;

import javax.persistence.*;


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

    @Override
    public Owner update(MemberDto.MemberUpdateDto memberUpdateDto) {
        super.update(memberUpdateDto);
        return this;
    }
}
